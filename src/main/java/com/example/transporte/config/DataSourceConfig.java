package com.example.transporte.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;

import lombok.extern.slf4j.Slf4j;
import oracle.jdbc.pool.OracleDataSource;

@Slf4j
@Configuration
public class DataSourceConfig {

	@Value("${spring.datasource.driverClassName}")
	String driverName;

	@Value("${spring.datasource.jndi-name}")
	String jndiname;

	@Value("${spring.datasource.url}")
	String urlDatasource;

	@Value("${spring.datasource.data-username}")
	String userConnection;

	@Value("${spring.datasource.data-password}")
	String passConnection;

	@Bean(destroyMethod = "", name = "datasource")
	public DataSource dataSource() {
		DataSource datasource = null;
		try {
			JndiDataSourceLookup dataSourceLookup = new JndiDataSourceLookup();
			datasource = dataSourceLookup.getDataSource(jndiname);

			if (datasource instanceof OracleDataSource) {
				log.info("El DataSource es de tipo OracleDataSource");

			} else {
				log.warn("El DataSource no es de tipo OracleDataSource. Se recomienda configurar un OracleDataSource.");

			}

			log.info("Nombre del DATASOURCE: " + jndiname);
		} catch (Exception e) {
			log.error("ERROR: no se encuentra el DATASOURCE con el nombre: " + jndiname
					+ ". Se configura el DATASOURCE de pruebas.");
			datasource = getDevelopmentDS();
		}
		return datasource;
	}

	/**
	 * Gets the development DS.
	 *
	 * @return the development DS
	 */
	private DataSource getDevelopmentDS() {
		DataSource datasource = null;
		try {
			log.info(userConnection);
			log.info(driverName);
			OracleDataSource oracleDataSource = new OracleDataSource();
			oracleDataSource.setURL(urlDatasource);
			oracleDataSource.setUser(userConnection);
			oracleDataSource.setPassword(passConnection);
			datasource = oracleDataSource;
 
			datasource = oracleDataSource;
		} catch (Exception e) {
			log.error(e.getMessage());
			log.error("ERROR: no se encuentra el DATASOURCE de desarrollo: " + urlDatasource + ". ERROR: " + e, e);
		}
		return datasource;
	}

}