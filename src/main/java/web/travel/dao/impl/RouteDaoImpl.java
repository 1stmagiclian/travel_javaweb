package web.travel.dao.impl;

import web.travel.dao.RouteDao;
import web.travel.domain.Route;
import web.travel.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;

public class RouteDaoImpl implements RouteDao {
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    @Override
    public int findTotalCount(int cid,String rname) {
        String sql = "select count(*) from tab_route where 1=1 ";
        StringBuilder sb = new StringBuilder(sql);

        List params = new ArrayList();//条件们
        //2.判断参数是否有值
        if (cid != 0) {
            sb.append(" and cid=? ");

            params.add(cid);//添加？对应的值
        }

        if (rname != null && rname.length() > 0 && !"null".equals(rname)) {
            sb.append(" and rname like ? ");

            params.add("%" + rname + "%");
        }

        sql = sb.toString();


        return template.queryForObject(sql, Integer.class, params.toArray());
    }
    @Override
    public List<Route> findByPage(int cid, int start, int pageSize,String rname) {
        //String sql = "select * from tab_route where cid = ? and rname like ?  limit ? , ?";
        String sql = " select * from tab_route where 1 = 1  ";
        //1.定义sql模板
        StringBuilder sb = new StringBuilder(sql);

        List params = new ArrayList();
        //2.判断参数是否有值
        if(cid != 0){
            sb.append(" and cid=? ");

            params.add(cid);
        }

        if(rname != null && rname.length() > 0 && !"null".equals(rname)){
            sb.append("and rname like ? ");

            params.add("%"+rname+"%");
        }
        sb.append(" limit ?,? ");

        sql = sb.toString();

        params.add(start);
        params.add(pageSize);


        return template.query(sql,new BeanPropertyRowMapper<Route>(Route.class),params.toArray());
    }

    @Override
    public Route findOne(int rid) {
        String sql = "SELECT * FROM  tab_route WHERE rid = ?";
        return template.queryForObject(sql,new BeanPropertyRowMapper<Route>(Route.class),rid);
    }
}
