package com.helospark.site.core.service.gallery.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.helospark.site.core.service.gallery.domain.GalleryEntity;

@Repository
public interface GalleryEntityRepository extends CrudRepository<GalleryEntity, String> {

}
