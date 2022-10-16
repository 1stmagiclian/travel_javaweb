package web.travel.service;

import web.travel.domain.PageBean;
import web.travel.domain.Route;

/**
 * 线路分页
 */
public interface RouteService {

    /**
     * 根据类别查询
     * @param cid
     * @param currentPage
     * @param pageSize
     * @return
     */
    public PageBean<Route> pageQuery(int cid,int currentPage,int pageSize,String rname);

    /**
     * 根据id查询
     * @param rid
     * @return
     */
    public Route findOne(String rid);
}
