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

package com.liferay.headless.form.graphql.v1_0.test;

import com.liferay.headless.form.client.dto.v1_0.Form;
import com.liferay.headless.form.client.http.HttpInvoker;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.CompanyLocalServiceUtil;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.vulcan.graphql.query.GraphQLField;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import javax.annotation.Generated;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public abstract class BaseFormGraphQLTestCase {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		testGroup = GroupTestUtil.addGroup();

		testCompany = CompanyLocalServiceUtil.getCompany(
			testGroup.getCompanyId());
	}

	@After
	public void tearDown() throws Exception {
		GroupTestUtil.deleteGroup(testGroup);
	}

	@Test
	public void testGetForm() throws Exception {
		Form postForm = testGetForm_addForm();

		List<GraphQLField> graphQLFields = new ArrayList<>();

		graphQLFields.add(new GraphQLField("id"));

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			graphQLFields.add(new GraphQLField(additionalAssertFieldName));
		}

		GraphQLField graphQLField = new GraphQLField(
			"query",
			new GraphQLField(
				"getForm",
				new HashMap<String, Object>() {
					{
						put("formId", postForm.getId());
					}
				},
				graphQLFields.toArray(new GraphQLField[0])));

		JSONObject responseJSONObject = JSONFactoryUtil.createJSONObject(
			_invoke(graphQLField.toString()));

		JSONObject dataJSONObject = responseJSONObject.getJSONObject("data");

		Assert.assertTrue(
			equals(postForm, dataJSONObject.getJSONObject("getForm")));
	}

	protected Form testGetForm_addForm() throws Exception {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected boolean equals(Form form, JSONObject jsonObject) {
		List<String> fieldNames = new ArrayList(
			Arrays.asList(getAdditionalAssertFieldNames()));

		fieldNames.add("id");

		for (String fieldName : fieldNames) {
			if (Objects.equals("defaultLanguage", fieldName)) {
				if (!Objects.equals(
						form.getDefaultLanguage(),
						(String)jsonObject.getString("defaultLanguage"))) {

					return false;
				}

				continue;
			}

			if (Objects.equals("description", fieldName)) {
				if (!Objects.equals(
						form.getDescription(),
						(String)jsonObject.getString("description"))) {

					return false;
				}

				continue;
			}

			if (Objects.equals("id", fieldName)) {
				if (!Objects.equals(
						form.getId(), (Long)jsonObject.getLong("id"))) {

					return false;
				}

				continue;
			}

			if (Objects.equals("name", fieldName)) {
				if (!Objects.equals(
						form.getName(), (String)jsonObject.getString("name"))) {

					return false;
				}

				continue;
			}

			if (Objects.equals("siteId", fieldName)) {
				if (!Objects.equals(
						form.getSiteId(), (Long)jsonObject.getLong("siteId"))) {

					return false;
				}

				continue;
			}

			if (Objects.equals("structureId", fieldName)) {
				if (!Objects.equals(
						form.getStructureId(),
						(Long)jsonObject.getLong("structureId"))) {

					return false;
				}

				continue;
			}

			throw new IllegalArgumentException(
				"Invalid field name " + fieldName);
		}

		return true;
	}

	protected String[] getAdditionalAssertFieldNames() {
		return new String[0];
	}

	protected Form randomForm() throws Exception {
		return new Form() {
			{
				dateCreated = RandomTestUtil.nextDate();
				dateModified = RandomTestUtil.nextDate();
				datePublished = RandomTestUtil.nextDate();
				defaultLanguage = RandomTestUtil.randomString();
				description = RandomTestUtil.randomString();
				id = RandomTestUtil.randomLong();
				name = RandomTestUtil.randomString();
				siteId = testGroup.getGroupId();
				structureId = RandomTestUtil.randomLong();
			}
		};
	}

	private String _invoke(String query) throws Exception {
		HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

		JSONObject jsonObject = JSONUtil.put("query", query);

		httpInvoker.body(jsonObject.toString(), "application/json");

		httpInvoker.httpMethod(HttpInvoker.HttpMethod.POST);
		httpInvoker.path("http://localhost:8080/o/graphql");
		httpInvoker.userNameAndPassword("test@liferay.com:test");

		HttpInvoker.HttpResponse httpResponse = httpInvoker.invoke();

		return httpResponse.getContent();
	}

	protected Company testCompany;
	protected Group testGroup;

}