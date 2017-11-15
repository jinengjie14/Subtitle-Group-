package com.zzz.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.zzz.model.Comment;
import com.zzz.model.Task;
import com.zzz.model.User;

@Repository
@Transactional
public class CommentRepository {
	@PersistenceContext
	private EntityManager entityManager;
	public Session getSession() {
		return entityManager.unwrap(Session.class);
	}
	public void save(Comment comment) {
		this.getSession().save(comment);
	}
	public void update(Comment comment) {
		this.getSession().merge(comment);
	}
	public void delete(Comment comment) {
		this.getSession().delete(comment);
	}
	public List<Comment> select (){
		DetachedCriteria dc = DetachedCriteria.forClass(Comment.class);
		Criteria criteria = dc.getExecutableCriteria(getSession());//在线获取seesion
		List<Comment> list=criteria.list();
		return list;
	}
	public List taskdetails(String id) {
		   String hql = "SELECT c,u.name,u.path FROM Task AS t,Comment AS c,User AS u "
				   + "WHERE c.user=u.user_id AND c.task=t.task_id AND t.task_id='"+id+"'";
		   Query query = getSession().createQuery(hql);
		return query.list();
	}
}
