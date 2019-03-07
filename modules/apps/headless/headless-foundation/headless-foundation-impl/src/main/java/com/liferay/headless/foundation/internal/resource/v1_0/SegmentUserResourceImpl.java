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

package com.liferay.headless.foundation.internal.resource.v1_0;

import com.liferay.headless.foundation.dto.v1_0.SegmentUser;
import com.liferay.headless.foundation.resource.v1_0.SegmentUserResource;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserService;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.segments.provider.SegmentsEntryProvider;

import java.util.ArrayList;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Javier Gamarra
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/segment-user.properties",
	scope = ServiceScope.PROTOTYPE, service = SegmentUserResource.class
)
public class SegmentUserResourceImpl extends BaseSegmentUserResourceImpl {

	@Override
	public Page<SegmentUser> getSegmentUserAccountsPage(
			Long segmentId, Pagination pagination)
		throws Exception {

		long[] segmentsEntryClassPKs =
			_segmentsEntryProvider.getSegmentsEntryClassPKs(
				segmentId, pagination.getStartPosition(),
				pagination.getEndPosition());

		List<User> users = new ArrayList<>(segmentsEntryClassPKs.length);

		for (long segmentsEntryClassPK : segmentsEntryClassPKs) {
			User user = _userService.getUserById(segmentsEntryClassPK);

			users.add(user);
		}

		return Page.of(
			transform(users, this::_toSegmentUser), pagination, users.size());
	}

	private SegmentUser _toSegmentUser(User user) {
		return new SegmentUser() {
			{
				email = user.getEmailAddress();
				id = user.getUserId();
				name = user.getFullName();
			}
		};
	}

	@Reference
	private SegmentsEntryProvider _segmentsEntryProvider;

	@Reference
	private UserService _userService;

}