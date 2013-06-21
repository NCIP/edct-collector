/*L
 * Copyright HealthCare IT, Inc.
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/edct-collector/LICENSE.txt for details.
 */

package com.healthcit.how.dao;

import org.apache.log4j.Logger;

import com.healthcit.how.models.Tag;

public class TagDao extends BaseJpaDao<Tag, String> {

		private static final Logger logger = Logger.getLogger(TagDao.class);

		public 	TagDao()
		{
			super(Tag.class);
		}

}
