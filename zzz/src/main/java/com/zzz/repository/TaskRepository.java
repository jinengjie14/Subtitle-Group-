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
import com.zzz.model.Task;
import com.zzz.model.User;

@Repository
@Transactional
public class TaskRepository {
	@PersistenceContext
	private EntityManager entityManager;
	public Session getSession() {
		return entityManager.unwrap(Session.class);
	}
	public void save(Task task) {
		this.getSession().save(task);
	}
	public void update(Task task) {
		this.getSession().merge(task);
	}
	public void delete(Task task) {
		this.getSession().delete(task);
	}
	/**
	 * 查询当前登录用户参与的任务且 state和delay等于0的任务
	 * @param user
	 * @return
	 */
	public List<Task> select(User user){
		DetachedCriteria dc = DetachedCriteria.forClass(Task.class);
		Criteria criteria = dc.getExecutableCriteria(getSession());//在线获取seesion
		int a=0;
		criteria.add(Restrictions.eq("user", user));
		criteria.add(Restrictions.eq("state", a)).add(Restrictions.eq("delay",a));
		List list=criteria.list();
		return list;
	}
	public List selecttask(String proid) {
		   String hql = "SELECT t,u.name FROM Project AS p,Task AS t,User AS u "
				   + "WHERE t.project=p.pro_id AND t.state=1 AND t.delay=0 AND t.user=u.user_id AND p.pro_id='"+proid+"'";
		   Query query = getSession().createQuery(hql);
		return query.list();
	
	}
	public List<Task> selectid(String task_id){
		DetachedCriteria dc = DetachedCriteria.forClass(Task.class);
		Criteria criteria = dc.getExecutableCriteria(getSession());//在线获取seesion
		criteria.add(Restrictions.eq("task_id", task_id));
		List<Task> list=criteria.list();
		return list;
	}
	public List<Task> prodelete(Project project) {
		DetachedCriteria dc = DetachedCriteria.forClass(Task.class);
		Criteria criteria = dc.getExecutableCriteria(getSession());//在线获取seesion
		criteria.add(Restrictions.eq("project", project));
		List<Task> list=criteria.list();
		return list;
	}
	public List selectuser(String content,String name,String pro_id) {
		   String hql = "SELECT t,u.name FROM Project AS p,Task AS t,User AS u "
				   + "WHERE t.project=p.pro_id AND t.state=1 AND t.delay=0 AND t.user=u.user_id AND";
		   Query query = getSession().createQuery(hql);
		return query.list();
	}
	//查询项目表下的任务
	public List selectProjecttask(String proid) {
		   String hql = "SELECT t,u.name,u.path FROM Project AS p,Task AS t,User AS u "
				   + "WHERE t.project=p.pro_id AND t.state=0 AND t.delay=0 AND t.user=u.user_id AND p.pro_id='"+proid+"'";
		   Query query = getSession().createQuery(hql);
		return query.list();
	}
}
