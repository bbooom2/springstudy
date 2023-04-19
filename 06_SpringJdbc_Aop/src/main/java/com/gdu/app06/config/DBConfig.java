package com.gdu.app06.config;

import java.util.Collections;

import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.interceptor.MatchAlwaysTransactionAttributeSource;
import org.springframework.transaction.interceptor.RollbackRuleAttribute;
import org.springframework.transaction.interceptor.RuleBasedTransactionAttribute;
import org.springframework.transaction.interceptor.TransactionInterceptor;

@EnableAspectJAutoProxy  // aop 동작을 허용한다. 
@EnableTransactionManagement //트랜젝션 관리를 허용하겠다. 트랜젝션 처리를 허용한다. (컨피그레이션 빈)  (DBConfig에서는 앞으로도 계속 사용할 애너테이션) 
@Configuration // 스프링 컨테이너에 Bean을 만들어두는 애너테이션
public class DBConfig {

	// DriverManagerDataSource Bean
	@Bean
	public DriverManagerDataSource dataSource() { // 드라이버매니저 :JDBC 커넥션 반환, 데이타소스 :DBCP 디비커넥션풀에서 커넥션 반환 // jdbc 방식을 사용할 건데 커넥션 풀 방식을 활용할 것음 의미함. 
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("oracle.jdbc.OracleDriver");
		dataSource.setUrl("jdbc:oracle:thin:@localhost:1521:xe");
		dataSource.setUsername("GDJ61");
		dataSource.setPassword("1111");
		return dataSource;
	}
	
	// JdbcTemplate Bean (Connection, PreparedStatement, ResultSet을 이용해서 동작하는 스프링 클래스 
	@Bean
	public JdbcTemplate jdbcTemplate() {
		return new JdbcTemplate(dataSource()); // DriverManagerDataSource Bean을 JdbcTemplate 생성자에 주입 // dataSource() 위에 만들어놓은거 호출한 것. 
		//스프링컨테이너에 빈 두개를 만들었는데 첫번째 빈은 dataSource 두번째는 jdbcTemplate 빈의 이름은 메소드 이름으로 결정한다. 명확하게 하고싶으면 Bean(name="") 이렇게 하고 따옴표 안에 넣으면 된다. 
		//DAO열어서 Connection, PreparedStatement, ResultSet 손보기 
	}
	
	// 아래 부분은 AOP를 이용해서 트랜잭션 처리를 하기 위한 Bean
	
	
	// DataSourceTransactionManager Bean 
	@Bean
	public TransactionManager transactionManager() {
		return new DataSourceTransactionManager(dataSource()); // DriverManagerDataSource Bean을 DataSourceTransactionManager 생성자에 주입
		
	}
	
	// TransactionInterceptor Bean 
	@Bean
	public TransactionInterceptor transactionInterceptor() {
		
		// 모든 트랜잭션 처리에서 Exception이 발생하면 Rollback을 수행하시오.
		// 코드를 외우기 보다는 코드의 쓰임을 알고 있으면 됨. 
		RuleBasedTransactionAttribute ruleBasedTransactionAttribute = new RuleBasedTransactionAttribute();
		ruleBasedTransactionAttribute.setName("*"); // 이름 상관없이 동작시키기 위해 *
		ruleBasedTransactionAttribute.setRollbackRules(Collections.singletonList(new RollbackRuleAttribute(Exception.class))); // 언제 취소를 할겁니까. 리스트를 만드는 과정. 
		
		 MatchAlwaysTransactionAttributeSource source = new MatchAlwaysTransactionAttributeSource();
		 source.setTransactionAttribute(ruleBasedTransactionAttribute);
		
		return new TransactionInterceptor(transactionManager(), source); // 트랜젝션 매니저가 주입되었고, 두번째는 위에서 만들었던 룰베이스드를 기반으로 해서 애트리뷰트 소스값 넣음 
	}
	
	/*
	 	 AOP 관련 핵심 용어 3가지 
	 	 1. 조인포인트(JoinPoint) : AOP를 적용시킬 수 있는 메소드 전체 								- 목록, 상세, 삽입, 수정, 삭제 
	 	 2. 포인트컷(PointCut) : 조인포인트 중에서 AOP를 동작시킬 메소드  (조인포인트의 부분집합)   - 실제 aop 동작은 삽입, 수정, 삭제 
	 	 3. 어드바이스(Advice) : 포인트컷에 동작시킬 AOP 동작 자체 									- 트랜잭션 
	 */
	
	// Advisor Bean
	@Bean
	public Advisor advisor() {
		/*
	 		표현식(Expression) 작성 방법 
	 		1. 형식 
	 			execution(반환타입 패키지.클래스.메소드(매개변수))
	 		
	 		2. 의미 
	 			1) 반환타입
	 				(1) *  : 모든 반환타입 //인트건 보이드건 다 상관없음 // 일반적으로 많이 씀 
	 				(2) void : void 반환타입 
	 				(3) !void : void를 제외한 반환타입. 
	 			2) 매개변수 
	 				(1) .. : 모든 매개변수 
	 				(2) * : 1개의 모든 매개변수 
	 */
		
		
		// 포인트컷 설정(어드바이스(트랜잭션)를 동작시킬 메소드)
		AspectJExpressionPointcut pointCut = new AspectJExpressionPointcut();
		pointCut.setExpression("execution(* com.gdu.app06.service.BoardServiceImpl.*Tx(..))"); // BoardServiceImpl 클래스에 있는 메소드 중에서 이름이 Tx(트랜젝션 줄여서 이걸로 많이 사용) 로 끝나는 메소드
		return new DefaultPointcutAdvisor(pointCut, transactionInterceptor()); // 포인트컷으로 등록된 메소드에 트랜잭션인터셉터를 동작시키시오 (알아서 트랜젝션 처리하시오) 
	}
}
