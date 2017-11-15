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
import com.zzz.model.Taskman;
import com.zzz.model.User;
@Repository
@Transactional
public class ProjectmanRepository {

	
		@PersistenceContext
		private EntityManager entityManager;
		public Session getSession() {
			return entityManager.unwrap(Session.class);
		}
		public void save(Projectman projectman) {
			this.getSession().save(projectman);
		}
		public void update(Projectman projectman) {
			this.getSession().merge(projectman);
		}
		public void delete(Projectman projectman) {
			this.getSession().delete(projectman);
		}
		public List<Projectman> mandelete(Project project){
			DetachedCriteria dc = DetachedCriteria.forClass(Projectman.class);
			Criteria criteria = dc.getExecutableCriteria(getSession());//在线获取seesion
			criteria.add(Restrictions.eq("project", project));
			List<Projectman> list=criteria.list();
			return list;
		}
   	public List select(String id){
			   String hql = "SELECT u FROM User AS u,Project AS p,Projectman AS m "
					   + "WHERE u.user_id=m.user AND p.pro_id=m.project AND p.pro_id='"+id+"'";
			   Query query = getSession().createQuery(hql);
			return query.list();
		}
   	public List<Projectman> selectman(User user,Project project){
		DetachedCriteria dc = DetachedCriteria.forClass(Projectman.class);
		Criteria criteria = dc.getExecutableCriteria(getSession());//在线获取seesion
		criteria.add(Restrictions.eq("user", user));
		criteria.add(Restrictions.eq("project", project));
		List<Projectman> list=criteria.list();
		return list;
	}

}
