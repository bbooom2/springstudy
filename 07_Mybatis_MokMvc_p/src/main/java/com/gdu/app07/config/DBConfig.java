package com.gdu.app07.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment; //스프링 환경설정 , 스프링 세팅
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration 
@PropertySource(value= {"classpath:application.properties"}) //밸류 속성으로 애플리케이션프로퍼티스 경로를 지정하면 다른 객체에 앤비론먼트 객체에 자동으로 주입이된다.
@EnableTransactionManagement // 외부 클래스에서 트랜젝셔널애너테이션 붙였으면 애너테이션 붙은 애들 찾게 해줌. 클래스 내부에 트랜젝션매니저 빈이 사용되었을 경우에 트랜젝션을 허용해줌.  
public class DBConfig {
	
	@Autowired // 클래스간의 의존관계를 스프링컨테이너가 자동으로 연결해주는 애너테이션 
	private Environment env;
	
	// 스프링 DB 연결 
	
	@Bean
	public HikariConfig hikariConfig() { //데이터소스 정보 초기화,  환경설정안에 있으니까 히카리컨피그가 환경설정 객체 안에 있는 정보 불러와서 커넥션풀 할것. 
		HikariConfig hikariConfig = new HikariConfig();
		hikariConfig.setDriverClassName(env.getProperty("spring.datasource.hikari.driver-class-name"));  //드라이버로드하겠다. 가져오겠다. 오라클 드라이버 가져올것. 
		hikariConfig.setJdbcUrl(env.getProperty("spring.datasource.hikari.jdbc-url"));
		hikariConfig.setUsername(env.getProperty("spring.datasource.hikari.username"));
		hikariConfig.setPassword(env.getProperty("spring.datasource.hikari.password"));
		return hikariConfig;
	} // 관리하겠다. 
	
	@Bean(destroyMethod="close") // destroyMethod close: 빈객체의 역할이 끝날경우 close하겠다.
	public HikariDataSource hikariDataSource() { // 데이타 소스 관리 
		return new HikariDataSource(hikariConfig()); // 관리뿐만 아니라 닫겠다. 
	}
	
	//스프링 마이바티스 연결  - > 디비 스프링 마이바티스 이렇게 세개 연동시키기 위해서 한번더 set 처리 해주는 것 
	@Bean //  SqlSessionFactory = SqlSessionFactoryBean(스프링)
	public SqlSessionFactory sqlSessionFactory() throws Exception { // 데이터엑세스익셉션  = > 데이터 접속 실패 되니까 미리 처리해주는 것  
		SqlSessionFactoryBean bean = new SqlSessionFactoryBean();  // 스프링 컨테이너에서 빈객체로 등록되어있어야 하니까 해당 객체가 사용되는 것 
		bean.setDataSource(hikariDataSource()); 				  // 히카리데이타소스를 set 
		bean.setConfigLocation(new PathMatchingResourcePatternResolver().getResource(env.getProperty("mybatis.config-location"))); // 리졸버가 아래 리솔스를 마이바티스의 설정 파일과 매퍼의 경로임을 분석할 수 있는 것. 
		bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(env.getProperty("mybatis.mapper-locations")));
		return bean.getObject();
		
	}
	
	@Bean // Sqlsession = SqlSessionTemplate(스프링)
	public SqlSessionTemplate sqlSessionTemplate() throws Exception {
		return new SqlSessionTemplate(sqlSessionFactory());	 // 팩토리를 통해 세션이 만들어짐 
	}
	
	@Bean
	public TransactionManager transactionManager() {
		return new DataSourceTransactionManager(hikariDataSource()); // 트랜젝션 처리해주는 메소드. 생성해준 데이타소스 트랜젝션 매니저는 스프링에서 우리가 쓰는 드라이버가 제이디비씨 기반 라이브러리인 ojdbc8이 있기 때문에 이 드라이버로 구동시킬수있음. 디비접속에도 성공 실패가 나오는데 이 트랜젝션을 관리하는거고 이 처리를 히카리데이터소스 안에 디비 접속을 했고 디스트로이로 커넥션 종료까지 되어있는데  트랜젝션 매니저가 처리함. 
	}
	
	
}
