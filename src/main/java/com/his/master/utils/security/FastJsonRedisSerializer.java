package   com.his.master.utils.security;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * 实现RedisSerializer接口，并使用Fastjson库将Java对象序列化为JSON字符串，反序列化为Java对象。
 */
public class FastJsonRedisSerializer<T> implements RedisSerializer<T> {
    public static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;
    private Class<T> clazz;

    /**
     * 静态初始化器，设置ParserConfig的全局实例，并开启自动类型推断。
     */
    static {
        ParserConfig.getGlobalInstance().setAutoTypeSupport(true);
    }/**
     * 构造方法，接收一个Class对象作为参数，初始化RedisSerializer实例。
     * @param clazz 表示要序列化和反序列化的Java对象的Class对象。
     */
    public FastJsonRedisSerializer(Class<T> clazz) {
        super();
        this.clazz = clazz;
    }

    /**
     * 将Java对象序列化为JSON字符串。
     * 如果输入的对象为null，则返回一个空字节数组。
     * @param t 要序列化的Java对象。
     * @return 包含序列化JSON字符串的字节数组。
     */
    @Override
    public byte[] serialize(T t) throws SerializationException {
        if (t == null) {
            return new byte[0];
        }
        return JSON.toJSONString(t,
                SerializerFeature.WriteClassName).getBytes(DEFAULT_CHARSET);
    }

    /**
     * 将JSON字符串反序列化为Java对象。
     * 如果输入的字节数组为null或长度小于等于0，则返回null。
     * @param bytes 包含序列化JSON字符串的字节数组。
     * @return 从JSON字符串反序列化的Java对象。
     */
    @Override
    public T deserialize(byte[] bytes) throws SerializationException {
        if (bytes == null || bytes.length <= 0) {
            return null;
        }
        String str = new String(bytes, DEFAULT_CHARSET);
        return JSON.parseObject(str, clazz);
    }

    /**
     * 获取表示指定Class对象的JavaType对象。
     * @param clazz 表示需要JavaType对象的Class对象。
     * @return 表示指定Class对象的JavaType对象。
     */
    protected JavaType getJavaType(Class<?> clazz) {
        return TypeFactory.defaultInstance().constructType(clazz);
    }
}
