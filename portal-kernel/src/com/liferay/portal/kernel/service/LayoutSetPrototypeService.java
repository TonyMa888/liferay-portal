/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.kernel.service;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.jsonwebservice.JSONWebService;
import com.liferay.portal.kernel.model.LayoutSetPrototype;
import com.liferay.portal.kernel.security.access.control.AccessControlled;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.osgi.annotation.versioning.ProviderType;

/**
 * Provides the remote service interface for LayoutSetPrototype. Methods of this
 * service are expected to have security checks based on the propagated JAAS
 * credentials because this service can be accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see LayoutSetPrototypeServiceUtil
 * @generated
 */
@AccessControlled
@JSONWebService
@ProviderType
@Transactional(
	isolation = Isolation.PORTAL,
	rollbackFor = {PortalException.class, SystemException.class}
)
public interface LayoutSetPrototypeService extends BaseService {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add custom service methods to <code>com.liferay.portal.service.impl.LayoutSetPrototypeServiceImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface. Consume the layout set prototype remote service via injection or a <code>org.osgi.util.tracker.ServiceTracker</code>. Use {@link LayoutSetPrototypeServiceUtil} if injection and service tracking are not available.
	 */
	public LayoutSetPrototype addLayoutSetPrototype(
			Map<Locale, String> nameMap, Map<Locale, String> descriptionMap,
			boolean active, boolean layoutsUpdateable,
			boolean readyForPropagation, ServiceContext serviceContext)
		throws PortalException;

	public LayoutSetPrototype addLayoutSetPrototype(
			Map<Locale, String> nameMap, Map<Locale, String> descriptionMap,
			boolean active, boolean layoutsUpdateable,
			ServiceContext serviceContext)
		throws PortalException;

	public void deleteLayoutSetPrototype(long layoutSetPrototypeId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public LayoutSetPrototype fetchLayoutSetPrototype(long layoutSetPrototypeId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public LayoutSetPrototype getLayoutSetPrototype(long layoutSetPrototypeId)
		throws PortalException;

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public String getOSGiServiceIdentifier();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<LayoutSetPrototype> search(
			long companyId, Boolean active,
			OrderByComparator<LayoutSetPrototype> orderByComparator)
		throws PortalException;

	public LayoutSetPrototype updateLayoutSetPrototype(
			long layoutSetPrototypeId, Map<Locale, String> nameMap,
			Map<Locale, String> descriptionMap, boolean active,
			boolean layoutsUpdateable, boolean readyForPropagation,
			ServiceContext serviceContext)
		throws PortalException;

	public LayoutSetPrototype updateLayoutSetPrototype(
			long layoutSetPrototypeId, Map<Locale, String> nameMap,
			Map<Locale, String> descriptionMap, boolean active,
			boolean layoutsUpdateable, ServiceContext serviceContext)
		throws PortalException;

	public LayoutSetPrototype updateLayoutSetPrototype(
			long layoutSetPrototypeId, String settings)
		throws PortalException;

}