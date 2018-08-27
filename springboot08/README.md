Spring Boot 开发一个web应用程序时，你可以通过使用嵌入式tomcat,jetty,Undertow,或者Netty来实现内置http Server。在spring boot中，只需要添加``spring-boot-starter-web``模块可以速启动一个web应用。

#### 自定义的JSON序列化和反序列化
如果你使用Jackson去序列化和反序列化，当然，你也可以实现自己的序列化类，自定义的序列化类通过注册模块注册到Jackson，但是Spring Boot提供了另外一种实现方式通过注解``@JsonComponet``的方式轻松的注入到你的bean中。

#####1，Serialization
创建一个user对象：

	public class User {
	    private Color favoriteColor;
	 
	    // standard getters/constructors
	}

如果我们使用默认的配置去序列化得到的结果为：
	
	{
	  "favoriteColor": {
	    "red": 0.9411764740943909,
	    "green": 0.9725490212440491,
	    "blue": 1.0,
	    "opacity": 1.0,
	    "opaque": true,
	    "hue": 208.00000000000003,
	    "saturation": 0.05882352590560913,
	    "brightness": 1.0
	  }
	}


我们可以通过打印RGB值使JSON更加实用和可读，例如，在CSS中使用,如果我们自定义一个实现``JsonSerializer``的类：
	
	@JsonComponent
	public class UserJsonSerializer extends JsonSerializer<User> {
	 
	    @Override
	    public void serialize(User user, JsonGenerator jsonGenerator, 
	      SerializerProvider serializerProvider) throws IOException, 
	      JsonProcessingException {
	  
	        jsonGenerator.writeStartObject();
	        jsonGenerator.writeStringField(
	          "favoriteColor", 
	          getColorAsWebColor(user.getFavoriteColor()));
	        jsonGenerator.writeEndObject();
	    }
	 
	    private static String getColorAsWebColor(Color color) {
	        int r = (int) Math.round(color.getRed() * 255.0);
	        int g = (int) Math.round(color.getGreen() * 255.0);
	        int b = (int) Math.round(color.getBlue() * 255.0);
	        return String.format("#%02x%02x%02x", r, g, b);
	    }

最终实例化的结果为：

	{"favoriteColor":"#f0f8ff"}

通过使用``@JsonComponent ``注解，在springboot中实例化对象被注册在Jackson ObjectMapper 中，单元测试：

	@JsonTest
	@RunWith(SpringRunner.class)
	public class UserJsonSerializerTest {
	 
	    @Autowired
	    private ObjectMapper objectMapper;
	 
	    @Test
	    public void testSerialization() throws JsonProcessingException {
	        User user = new User(Color.ALICEBLUE);
	        String json = objectMapper.writeValueAsString(user);
	  
	        assertEquals("{\"favoriteColor\":\"#f0f8ff\"}", json);
	    }
	}

#####2，反序列化
和序列化类似：

	@JsonComponent
	public class UserJsonDeserializer extends JsonDeserializer<User> {
	  
	    @Override
	    public User deserialize(JsonParser jsonParser, 
	      DeserializationContext deserializationContext) throws IOException, 
	      JsonProcessingException {
	  
	        TreeNode treeNode = jsonParser.getCodec().readTree(jsonParser);
	        TextNode favoriteColor
	          = (TextNode) treeNode.get("favoriteColor");
	        return new User(Color.web(favoriteColor.asText()));
	    }
	}

单元测试：

	@JsonTest
	@RunWith(SpringRunner.class)
	public class UserJsonDeserializerTest {
	 
	    @Autowired
	    private ObjectMapper objectMapper;
	 
	    @Test
	    public void testDeserialize() throws IOException {
	        String json = "{\"favoriteColor\":\"#f0f8ff\"}"
	        User user = objectMapper.readValue(json, User.class);
	  
	        assertEquals(Color.ALICEBLUE, user.getFavoriteColor());
	    }
	}
