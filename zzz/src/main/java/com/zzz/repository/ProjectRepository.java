package com.zzz.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


import com.zzz.model.Project;
import com.zzz.model.Projectman;
import com.zzz.model.Task;
import com.zzz.model.User;

@Repository
@Transactional
public class ProjectRepository {
	@PersistenceContext
	private EntityManager entityManager;
	public Session getSession() {
		return entityManager.unwrap(Session.class);
	}
	public void save(Project project) {
		this.getSession().save(project);
	}
	public void update(Project project) {
		this.getSession().merge(project);
	}
	public void delete(Project project) {
		this.getSession().delete(project);
	}
	/**
	 * 1.传入用户id
	 * 2.根据用户id查询该用户参与的项目
	 * @param id
	 * @return
	 */
	public List<Project> select(String id){
		   String hql = "SELECT p FROM User AS u,Project AS p,Projectman AS m "
				   + "WHERE u.user_id=m.user AND p.pro_id=m.project AND u.user_id='"+id+"'";
		   Query query = getSession().createQuery(hql);
		return query.list();
	}
	/**
	 * 1.传入项目id
	 * 2.根据项目id查询出此项目
	 * @param proid
	 * @return
	 */
	public List<Project> selectpro(String pro_id) {
		DetachedCriteria dc = DetachedCriteria.forClass(Project.class);
		Criteria criteria = dc.getExecutableCriteria(getSession());//在线获取seesion
		criteria.add(Restrictions.eq("pro_id", pro_id));
		List<Project> list=criteria.list();
		return list;
	}
	/**
	 * 根据leader_id查询出项目数据
	 * @param user
	 * @return
	 */
	public List<Project> selectuser(User user){
		
		DetachedCriteria dc = DetachedCriteria.forClass(Project.class);
		Criteria criteria = dc.getExecutableCriteria(getSession());//在线获取seesion
		criteria.add(Restrictions.eq("user", user));
		List<Project> list=criteria.list();
		return list;
	}
	public void namupdate(String id,String name) {
		String hql="update Project p set p.pro_name='"+name+"' where p.pro_id='"+id+"'";
		Query query  = getSession().createQuery(hql); 
		query.executeUpdate();
	}
	public void contentupdate(String id,String content) {
		String hql="update Project p set p.content='"+content+"' where p.pro_id='"+id+"'";
		Query query  = getSession().createQuery(hql); 
		query.executeUpdate();
	}
}
