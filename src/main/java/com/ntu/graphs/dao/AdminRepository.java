package com.ntu.graphs.dao;

import com.ntu.graphs.entity.Admin;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends Neo4jRepository<Admin,Long> {
    boolean existsByUsername(String username);
    Admin save(Admin admin);

}
