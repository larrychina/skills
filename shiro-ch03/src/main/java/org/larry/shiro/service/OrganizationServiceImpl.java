package org.larry.shiro.service;

import org.larry.shiro.dao.OrganizationDao;
import org.larry.shiro.entity.Organization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
/**
 * <p>User: Zhang Kaitao
 * <p>Date: 14-2-14
 * <p>Version: 1.0
 */
@Service
public class OrganizationServiceImpl implements OrganizationService {
    @Autowired
    private OrganizationDao organizationDao;

    public Organization createOrganization(Organization organization) {
        return organizationDao.createOrganization(organization);
    }

    public Organization updateOrganization(Organization organization) {
        return organizationDao.updateOrganization(organization);
    }

    public void deleteOrganization(Long organizationId) {
        organizationDao.deleteOrganization(organizationId);
    }

    public Organization findOne(Long organizationId) {
        return organizationDao.findOne(organizationId);
    }

    public List<Organization> findAll() {
        return organizationDao.findAll();
    }

    public List<Organization> findAllWithExclude(Organization excludeOraganization) {
        return organizationDao.findAllWithExclude(excludeOraganization);
    }

    public void move(Organization source, Organization target) {
        organizationDao.move(source, target);
    }
}
