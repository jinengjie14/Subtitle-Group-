package com.zzz.repository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;



import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.zzz.model.Project;
import com.zzz.model.Task;
import com.zzz.model.Taskman;
import com.zzz.model.User;
@Repository
@Transactional
public class TaskmanRepository {
	@PersistenceContext
	private EntityManager entityManager;
	public Session getSession() {
		return entityManager.unwrap(Session.class);
	}
	public void save(Taskman taskman) {
		this.getSession().save(taskman);
	}
	public void update(Taskman taskman) {
		this.getSession().merge(taskman);
	}
	public void delete(Taskman taskman) {
		this.getSession().delete(taskman);
	}
	public List select(String id){
		   String hql = "SELECT u FROM User AS u,Task AS t,Taskman AS m "
				   + "WHERE u.user_id=m.user AND t.task_id=m.task AND t.task_id='"+id+"'";
		   Query query = getSession().createQuery(hql);
		return query.list();
	}
	public List selecttask(String id){
		   String hql = "SELECT t FROM User AS u,Task AS t,Taskman AS m "
				   + "WHERE u.user_id=m.user AND t.task_id=m.task AND state=0 AND delay=0 AND u.user_id='"+id+"'";
		   Query query = getSession().createQuery(hql);
		return query.list();
	}
	public void namupdate(String id,String name) {
		String hql="update Task t set t.name='"+name+"' where t.id='"+id+"'";
		Query query  = getSession().createQuery(hql); 
		query.executeUpdate();
	}
	public void contentupdate(String id,String content) {
		String hql="update Task t set t.content='"+content+"' where t.id='"+id+"'";
		Query query  = getSession().createQuery(hql); 
		query.executeUpdate();
	}
	public void proupdate(String id,Project pro) {
		String hql="update Task t set t.project='"+pro+"' where t.id='"+id+"'";
		Query query  = getSession().createQuery(hql); 
		query.executeUpdate();
	}
	
	public void dateupdate(String id,String date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			 try {
				sdf.parse(date);
				String hql="update Task t set t.bydate='"+date+"' where t.id='"+id+"'";
				Query query  = getSession().createQuery(hql); 
				query.executeUpdate();

			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	public List<Taskman> selectman(User user,Task task){
		DetachedCriteria dc = DetachedCriteria.forClass(Taskman.class);
		Criteria criteria = dc.getExecutableCriteria(getSession());//在线获取seesion
		criteria.add(Restrictions.eq("user", user));
		criteria.add(Restrictions.eq("task", task));
		List<Taskman> list=criteria.list();
		return list;
	}
}
