package com.helospark.site.core.service.gallery.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.helospark.site.core.service.gallery.domain.GalleryImageEntity;

@Repository
public interface GalleryImageEntityRepository extends CrudRepository<GalleryImageEntity, String> {

}
