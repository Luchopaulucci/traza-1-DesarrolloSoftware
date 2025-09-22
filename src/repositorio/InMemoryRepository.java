package repositorio;

import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

public class InMemoryRepository<T> {
    private final Map<Long, T> data = new HashMap<>();
    private final AtomicLong idGenerator = new AtomicLong();

    public T save(T entity) {
        long id = idGenerator.incrementAndGet();
        try {
            Method setId = entity.getClass().getMethod("setId", Long.class);
            setId.invoke(entity, id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        data.put(id, entity);
        return entity;
    }

    public Optional<T> findById(Long id) {
        return Optional.ofNullable(data.get(id));
    }

    public List<T> findAll() {
        return new ArrayList<>(data.values());
    }

    public Optional<T> update(Long id, T updatedEntity) {
        if (!data.containsKey(id)) {
            return Optional.empty();
        }
        try {
            Method setId = updatedEntity.getClass().getMethod("setId", Long.class);
            setId.invoke(updatedEntity, id);
            data.put(id, updatedEntity);
            return Optional.of(updatedEntity);
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    public Optional<T> delete(Long id) {
        if (!data.containsKey(id)) {
            return Optional.empty();
        }
        return Optional.ofNullable(data.remove(id));
    }

    public List<T> findByField(String fieldName, Object value) {
        List<T> results = new ArrayList<>();
        try {
            for (T entity : data.values()) {
                Method getField = entity.getClass().getMethod("get" + capitalize(fieldName));
                Object fieldValue = getField.invoke(entity);
                if (fieldValue != null && fieldValue.equals(value)) {
                    results.add(entity);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return results;
    }

    private String capitalize(String str) {
        if (str == null || str.isEmpty()) return str;
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
}
