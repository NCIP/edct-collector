package com.healthcit.how.dao;

import java.util.List;

import javax.persistence.Query;

import org.apache.log4j.Logger;

import com.healthcit.how.models.User;

public class UserDao extends BaseJpaDao< User, Long > {
	
	@SuppressWarnings("unused")
	private Logger log = Logger.getLogger( UserDao.class );
	
	/* HQL Queries */
	private static final String FIND_EMAIL_HQL = "select u from User u where u.emailAddress = :email";
	private static final String CHECK_DUPLICATE_EMAIL_HQL = "select count(emailAddress) from User u where u.emailAddress = :email";
	private static final String CHECK_DUPLICATE_USERNAME_HQL = "select count(username) from User u where u.username = :username";
	private static final String FIND_ALL_EMAILS_HQL = "from User";

	public UserDao()
	{
		super(User.class);
	}
	
	public User findByEmail( String email ) {
		Query query = em.createQuery( FIND_EMAIL_HQL );
		query.setParameter( "email", email );
		User u = ( User ) query.getResultList().get( 0 );
		return u;
	}
	
	public boolean doesEmailExist( String email ) {
		Query query = em.createQuery( CHECK_DUPLICATE_EMAIL_HQL );
		query.setParameter( "email", email );
		Long count = ( Long ) query.getResultList().get( 0 );
		return count >= 1;
	}
	
	public boolean doesUsernameExist( String username ) {
		Query query = em.createQuery( CHECK_DUPLICATE_USERNAME_HQL );
		query.setParameter( "username", username );
		Long count = ( Long ) query.getResultList().get( 0 );
		return count >= 1;
	}
	
	public List<User> findAllUsers() {
		Query query = em.createQuery( FIND_ALL_EMAILS_HQL );
		@SuppressWarnings("unchecked") List<User> users = ( List<User> ) query.getResultList();
		return users;
	}
}
