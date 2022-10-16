package web.travel.dao.impl;

import web.travel.dao.CategoryDao;
import web.travel.domain.Category;
import web.travel.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class CategoryDaoImpl implements CategoryDao {
   private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());
    @Override
    public List<Category> findAll() {
        String sql = "SELECT * FROM tab_category";
        return template.query(sql,new BeanPropertyRowMapper<Category>(Category.class));
    }
}
