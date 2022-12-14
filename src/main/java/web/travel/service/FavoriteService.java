package web.travel.service;

public interface FavoriteService {
    /**
     * 判断是否收藏过
     * @param rid
     * @param uid
     * @return
     */
    public boolean isFavorite(String rid,int uid);

    /**
     * 添加收藏
     * @param rid
     * @param uid
     */
    public void add(String rid, int uid);
}
