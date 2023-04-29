package com.example.stc.repositories;


import com.example.stc.domain.Items;
import com.example.stc.model.FileMetadataDTO;
import com.example.stc.model.ItemType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface ItemsRepository extends JpaRepository<Items, Long> {
    Items findByNameAndType(String name, ItemType type);

    @Query(value = "SELECT I.\"name\" ,CONCAT(ROUND(LENGTH(F.\"binary\") / 1048576.0,3) , ' MB') AS \"size\" FROM ITEMS I INNER JOIN FILES F ON I.ID = F.ITEM_ID WHERE I.ID = :id", nativeQuery = true)
    FileMetadataDTO findFileMetadata(@Param("id") Long id);

}
