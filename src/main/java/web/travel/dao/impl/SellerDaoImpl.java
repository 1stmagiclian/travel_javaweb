package web.travel.dao.impl;

import web.travel.dao.SellerDao;
import web.travel.domain.Seller;
import web.travel.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

public class SellerDaoImpl implements SellerDao {
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    @Override
    public Seller findById(int id) {
        String sql = "SELECT * FROM tab_seller WHERE sid=?";
        return template.queryForObject(sql,new BeanPropertyRowMapper<Seller>(Seller.class),id);
    }
}
