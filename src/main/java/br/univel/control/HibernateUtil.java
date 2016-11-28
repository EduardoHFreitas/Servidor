package br.univel.control;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class HibernateUtil {

	private static SessionFactory sessionFactory;
	private static StandardServiceRegistry registry;

	/* Build */
	private static SessionFactory buildSessionFactory() {

		registry = new StandardServiceRegistryBuilder().configure().build();

		SessionFactory sessionFactory = null;
		try {
			sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
		} catch (Exception e) {
			e.printStackTrace();
			StandardServiceRegistryBuilder.destroy(registry);
		}
		return sessionFactory;
	}

	/* Metodo Get */
	public static Session getSession() {
		if (sessionFactory == null){
			sessionFactory = buildSessionFactory();
		}
		return sessionFactory.openSession();
	}

	/* Destruir Sessao */
	public static void killSession() {
		StandardServiceRegistryBuilder.destroy(registry);
	}
}