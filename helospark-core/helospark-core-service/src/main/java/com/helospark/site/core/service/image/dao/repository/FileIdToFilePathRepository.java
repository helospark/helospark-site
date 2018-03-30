package com.helospark.site.core.service.image.dao.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileIdToFilePathRepository extends CrudRepository<LocalFileSystemMapping, String> {

}
