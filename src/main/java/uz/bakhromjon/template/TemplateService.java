package uz.bakhromjon.template;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.bakhromjon.config.CacheStore;
import uz.bakhromjon.exception.ElementNotFoundException;

import java.util.List;
import java.util.Optional;

/**
 * @author : Bakhromjon Khasanboyev
 * @username: @xbakhromjon
 * @since : 19/10/22, Wed, 09:51
 **/
@Service
public class TemplateService {

    @Autowired
    private TemplateRepository repository;

    @Autowired
    private CacheStore<Template> cacheStore;

    public ResponseEntity<?> create(Template template) {
        Template saved = repository.save(template);
        return ResponseEntity.ok(saved);
    }

    public ResponseEntity<?> update(Template template) {
        Template updated = repository.save(template);
        cacheStore.add(template.getId(), template);
        return ResponseEntity.ok(updated);
    }

    public ResponseEntity<?> get(Integer id) {
        // search template in cache
        Template template = cacheStore.get(id);
        if (template != null) {
            return ResponseEntity.ok(template);
        }
        // search template in DB
        Optional<Template> optional =
                repository.findById(id);
        template = optional.orElseThrow(() -> {
            throw new ElementNotFoundException("Element not found", HttpStatus.NOT_FOUND);
        });

        // save to cache
        cacheStore.add(template.getId(), template);
        return ResponseEntity.ok(template);
    }

    public ResponseEntity<?> list() {
        List<Template> all = repository.findAll();
        return ResponseEntity.ok(all);
    }

    public ResponseEntity<?> delete(Integer id) {
        cacheStore.remove(id);
        repository.deleteById(id);
        return ResponseEntity.ok(true);
    }
}
