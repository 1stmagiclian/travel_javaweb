package web.travel.service.impl;

import web.travel.dao.CategoryDao;
import web.travel.dao.impl.CategoryDaoImpl;
import web.travel.domain.Category;
import web.travel.service.CategoryService;
import web.travel.util.JedisUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CategoryServiceImpl implements CategoryService {
    private CategoryDao dao = new CategoryDaoImpl();
    @Override
    public List<Category> findAll() {
        //使用redis进行数据缓存，避免每次访问页面都从数据库获取数据，导致mysql库访问压力过大
        Jedis jedis = JedisUtil.getJedis();
        Set<Tuple> categorys = jedis.zrangeWithScores("category", 0, -1);
        List<Category> cs = null;
        //判断是否存在
        if (categorys == null || categorys.size() == 0) {
            //不存在
            cs = dao.findAll();
            for (int i = 0; i < cs.size(); i++) {
                jedis.zadd("category",cs.get(i).getCid(),cs.get(i).getCname());
            }
        }else {
            //将set集合存入list,数据结构保持一致
            cs = new ArrayList<Category>();
            for (Tuple tuple : categorys) {
                Category category = new Category();
                category.setCname(tuple.getElement());
                category.setCid((int)tuple.getScore());
                cs.add(category);
            }
        }
        return cs;
    }


}
