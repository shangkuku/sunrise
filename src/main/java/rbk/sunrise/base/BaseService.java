package rbk.sunrise.base;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.List;

/**
 * 可以通过重写非final方法来实现自己CRUD的逻辑
 * @param <T>
 * @param <PK>
 */
public class BaseService<T extends Entity, PK extends Serializable> {

    @Autowired
    protected BaseMapper<T> baseMapper;

    public T selectByPrimaryKey(PK pk) {
        return baseMapper.selectByPrimaryKey(pk);
    }

    public final T selectOne(T t) {
        return baseMapper.selectOne(t);
    }

    public final List<T> selectAll() {
        return baseMapper.selectAll();
    }

    public final List<T> selectByExample(Object example) {
        return baseMapper.selectByExample(example);
    }

    public final List<T> selectCountByExample(T t, RowBounds rowBounds) {
        return baseMapper.selectByRowBounds(t, rowBounds);
    }

    public final List<T> selectByExampleAndRowBounds(T t, RowBounds rowBounds) {
        return baseMapper.selectByExampleAndRowBounds(t, rowBounds);
    }

    public final int selectCount(T t) {
        return baseMapper.selectCount(t);
    }

    public final int selectCountByExample(Object example) {
        return baseMapper.selectCountByExample(example);
    }

    public final int insertSelective(T t) {
        return baseMapper.insertSelective(t);
    }

    public int insert(T t) {
        return baseMapper.insert(t);
    }

    public int updateByPrimaryKeySelective(T t) {
        return baseMapper.updateByPrimaryKeySelective(t);
    }

    public int updateByPrimaryKey(T t) {
        return baseMapper.updateByPrimaryKey(t);
    }

    public final int updateByExampleSelective(T t, Object example) {
        return baseMapper.updateByExampleSelective(t, example);
    }

    public final int updateByExample(T t, Object example) {
        return baseMapper.updateByExample(t, example);
    }

    public int deleteByPrimaryKey(PK pk) {
        return baseMapper.deleteByPrimaryKey(pk);
    }

    public final int deleteByExample(T t) {
        return baseMapper.deleteByExample(t);
    }
}
