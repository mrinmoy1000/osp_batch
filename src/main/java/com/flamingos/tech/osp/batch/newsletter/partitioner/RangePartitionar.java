/**
 * 
 */
package com.flamingos.tech.osp.batch.newsletter.partitioner;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

//import org.apache.log4j.Logger;
//import org.apache.log4j.spi.LoggerFactory;
import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

/**
 * @author Mrinmoy
 *
 */
public class RangePartitionar implements Partitioner {
	
	//private Logger LOGGER=Logger.getLogger(getClass());

	@Autowired
	private NamedParameterJdbcTemplate oNamedParameterJdbcTemplate;

	private String rangeSql;
	
	private String partingModule;
	
	
	/**
	 * @param rangeSql the rangeSql to set
	 */
	public void setRangeSql(String rangeSql) {
		this.rangeSql = rangeSql;
	}


	/**
	 * @param partingModule the partingModule to set
	 */
	public void setPartingModule(String partingModule) {
		this.partingModule = partingModule;
	}


	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.batch.core.partition.support.Partitioner#partition
	 * (int)
	 */
	@SuppressWarnings("unchecked")
	public Map<String, ExecutionContext> partition(int gridSize) {
		Map<String, ExecutionContext> partitionContext = new HashMap<String, ExecutionContext>();

		Map<String, Long> recordMap = oNamedParameterJdbcTemplate
				.queryForObject(rangeSql,new HashMap<String,Object>(), new RowMapper<Map<String, Long>>() {

					public Map<String, Long> mapRow(ResultSet rs, int arg1)
							throws SQLException {
						Map<String, Long> result=new HashMap<String, Long>();
						result.put("MIN_ID", rs.getLong("MIN_ID"));
						result.put("MAX_ID", rs.getLong("MAX_ID"));
						result.put("TOT_COUNT", rs.getLong("TOT_COUNT"));
						return result;
					}
					
				});
		
		Long minId = recordMap.get("MIN_ID");
		Long maxId = recordMap.get("MAX_ID");
		Long totCount = recordMap.get("TOT_COUNT");

		Long pageSize = (totCount / gridSize);
		if ((totCount % gridSize) != 0) {
			pageSize++;
		}else if(pageSize==0){
			pageSize=1l;
		}
		Long fromId = minId;
		Long toId = pageSize;
		
		System.out
		.println("\nStarting "+partingModule +" Records : " + recordMap);
		//LOGGER.info("\nStarting "+partingModule +" Records : " + recordMap);
		for (int threadIndex = 1; threadIndex <= gridSize; threadIndex++) {
			ExecutionContext contextValue = new ExecutionContext();
			System.out
					.println("\nStarting "+partingModule +" : Thread_"
							+ threadIndex + " , fromId: " + fromId
							+ " , toId: " + toId);

			contextValue.put("fromId", fromId);
			contextValue.put("toId", toId);
			contextValue.put("pageSize", pageSize * 10);
			contextValue.putString("name", "Thread" + threadIndex);
			fromId = toId + 1;
			toId = toId + pageSize;

			partitionContext.put("partition" + threadIndex, contextValue);
		}

		return partitionContext;
	}

}
