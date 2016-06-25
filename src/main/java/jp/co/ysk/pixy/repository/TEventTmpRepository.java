package jp.co.ysk.pixy.repository;

import jp.co.ysk.pixy.entity.TEventTmp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

// JpaRepository ジェネリクスの第一引数にエンティティクラス、第二引数に主キーのフィールド型
@Repository
public interface TEventTmpRepository extends JpaRepository<TEventTmp, Integer>, JpaSpecificationExecutor<TEventTmp> {

}