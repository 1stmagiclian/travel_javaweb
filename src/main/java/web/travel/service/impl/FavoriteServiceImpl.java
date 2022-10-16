package web.travel.service.impl;

import web.travel.dao.FavoriteDao;
import web.travel.dao.impl.FavoriteDaoImpl;
import web.travel.domain.Favorite;
import web.travel.service.FavoriteService;

public class FavoriteServiceImpl implements FavoriteService{
    private FavoriteDao dao = new FavoriteDaoImpl();
    @Override
    public boolean isFavorite(String rid, int uid) {
        Favorite favorite = dao.findByRidAndUid(Integer.parseInt(rid), uid);
        return favorite != null;//有值则为true
    }

    @Override
    public void add(String rid, int uid) {
        dao.add(Integer.parseInt(rid),uid);
    }
}
