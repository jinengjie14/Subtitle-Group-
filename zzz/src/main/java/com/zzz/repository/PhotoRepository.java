package com.zzz.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Property;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.zzz.model.Photo;

@Repository
@Transactional
public class PhotoRepository {
	@PersistenceContext
	private EntityManager entityManager;
	@Autowired
	private JdbcTemplate jdbcTemplate;

	public Session getSession() {
		return entityManager.unwrap(Session.class);
	}

	public void save(Photo photo) {
		this.getSession().save(photo);
	}

	public List<Photo> register(String newname) {
		DetachedCriteria dc = DetachedCriteria.forClass(Photo.class);
		dc.add(Property.forName("title").eq(newname));// 按字段来比较
		Criteria criteria = dc.getExecutableCriteria(getSession());// 在线获取seesion
		List list = criteria.list();
		if (null != list && list.size() > 0)// 判断是否有值
		{
			return list;
		} else {
			return null;
		}
	}

	public List<Photo> share(String share) {
		DetachedCriteria dc = DetachedCriteria.forClass(Photo.class);
		dc.add(Property.forName("share").eq(share));// 按字段来比较
		Criteria criteria = dc.getExecutableCriteria(getSession());// 在线获取seesion
		List list = criteria.list();
		if (null != list && list.size() > 0)// 判断是否有值
		{
			return list;
		} else {
			return null;
		}
	}

	public void update(String share, String title) {
		jdbcTemplate.update("update Photo set share=? where title=?", new Object[] { share, title });
	}
}
