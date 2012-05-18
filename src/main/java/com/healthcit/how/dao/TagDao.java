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
