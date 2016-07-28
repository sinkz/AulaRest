package com.aula.testes;

import com.aula.util.HibernateUtil;

public class GenerateTables {

	public static void main(String[] args) {
		HibernateUtil.getSessionFactory();
		
		HibernateUtil.getSessionFactory().close();
	}
}
