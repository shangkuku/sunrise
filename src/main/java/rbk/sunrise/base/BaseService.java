package rbk.sunrise.base;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;


public class BaseService<T extends Entity, PK extends Serializable> {


    @Autowired
    protected BaseMapper<T> baseMapper;

    public T selectByPrimaryKey(PK pk) {
        return baseMapper.selectByPrimaryKey(pk);
    }

    public T selectOne(T t) {
        return baseMapper.selectOne(t);
    }

    public List<T> select(T t) {
        return baseMapper.select(t);
    }

    public List<T> selectAll() {
        return baseMapper.selectAll();
    }

    public List<T> selectByExample(Object example) {
        return baseMapper.selectByExample(example);
    }

    public List<T> selectCountByExample(T t, RowBounds rowBounds) {
        return baseMapper.selectByRowBounds(t, rowBounds);
    }

    public List<T> selectByExampleAndRowBounds(T t, RowBounds rowBounds) {
        return baseMapper.selectByExampleAndRowBounds(t, rowBounds);
    }

    public int selectCount(T t) {
        return baseMapper.selectCount(t);
    }

    public int selectCountByExample(Object example) {
        return baseMapper.selectCountByExample(example);
    }

    public int insertSelective(T t) {
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

    public int updateByExampleSelective(T t, Object example) {
        return baseMapper.updateByExampleSelective(t, example);
    }

    public int updateByExample(T t, Object example) {
        return baseMapper.updateByExample(t, example);
    }

    public int deleteByPrimaryKey(PK pk) {
        return baseMapper.deleteByPrimaryKey(pk);
    }

    public int deleteByExample(T t) {
        return baseMapper.deleteByExample(t);
    }
}
