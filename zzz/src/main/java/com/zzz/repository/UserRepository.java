package com.zzz.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Property;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;

import com.zzz.model.User;

@Repository
@Transactional
public class UserRepository {
	@PersistenceContext
	private EntityManager entityManager;
	
	public Session getSession() {
		return entityManager.unwrap(Session.class);
	}
	
	public void save(User user) {
		getSession().save(user);
	}
	
	public String auth(String account,String passwd)
	{
		DetachedCriteria dc = DetachedCriteria.forClass(User.class);
		dc.add(Property.forName("account").eq(account));//按字段来比较
		Criteria criteria = dc.getExecutableCriteria(getSession());//在线获取seesion
		List list=criteria.list();
		if(null != list && list.size()>0)//判断是否有值
		{
			User user=(User)list.get(0);//取出用户
			if(null!=user && StringUtils.equals(user.getPasswd(),passwd))
			//判断用户密码是否相同
			{
				return "success";
				//返回成功
			}
		}else{
			return "account_is_error";
		}
		return "";
	}
	
	public List<User> findbyid(String account)
	{
		DetachedCriteria dc = DetachedCriteria.forClass(User.class);
		dc.add(Property.forName("account").eq(account));//按字段来比较
		Criteria criteria = dc.getExecutableCriteria(getSession());//在线获取seesion
		List list=criteria.list();
		if(null != list && list.size()>0){
			return list;
		}
		return null;
	}
	public List<User> userid1(String a)
	{
		DetachedCriteria dc = DetachedCriteria.forClass(User.class);
		dc.add(Property.forName("id").eq(a));//按字段来比较
		Criteria criteria = dc.getExecutableCriteria(getSession());//在线获取seesion
		List list=criteria.list();
		if(null != list && list.size()>0){
			return list;
		}
		else{
		return null;
		}
	}
	
	public String register(String account)
	{
		DetachedCriteria dc = DetachedCriteria.forClass(User.class);
		dc.add(Property.forName("account").eq(account));//按字段来比较
		Criteria criteria = dc.getExecutableCriteria(getSession());//在线获取seesion
		List list=criteria.list();
		if(null != list && list.size()>0)//判断是否有值
		{
		}else{
			return "account_is_error";
		}
		return "";
	}
}
