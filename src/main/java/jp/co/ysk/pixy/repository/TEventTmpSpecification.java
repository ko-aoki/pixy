package jp.co.ysk.pixy.repository;

import jp.co.ysk.pixy.entity.MBusinessConnection;
import jp.co.ysk.pixy.entity.MCustomer;
import jp.co.ysk.pixy.entity.TEventTmp;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * イベントテンポラリの検索条件クラス.
 *
 */
public class TEventTmpSpecification {

    /**
     * 開始日付以上か検索する。
     */
    public static Specification<TEventTmp> startLessThanTarget(String date) {
        return StringUtils.isEmpty(date) ? null : new Specification<TEventTmp>() {
            @Override
            public Predicate toPredicate(Root<TEventTmp> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
    			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                try {
					return cb.lessThanOrEqualTo(root.get("startDatetime"), sdf.parse(date + " 23:59:59"));
				} catch (ParseException e) {
					throw new IllegalArgumentException(e);
				}
            }
        };
    }

    /**
     * 終了日付以下か検索する。
     */
    public static Specification<TEventTmp> endGreaterThanTarget(String date) {
        return StringUtils.isEmpty(date) ? null : new Specification<TEventTmp>() {
            @Override
            public Predicate toPredicate(Root<TEventTmp> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
    			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                try {
					return cb.greaterThanOrEqualTo(root.get("endDatetime"), sdf.parse(date + " 00:00:00"));
				} catch (ParseException e) {
					throw new IllegalArgumentException(e);
				}
            }
        };
    }

    /**
     * 来場者名称（姓）でイベントテンポラリを検索する。中間一致。
     */
    public static Specification<TEventTmp> visitorNameContains(String visitorName) {
        return StringUtils.isEmpty(visitorName) ? null : new Specification<TEventTmp>() {
            @Override
            public Predicate toPredicate(Root<TEventTmp> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

                Join<TEventTmp, MCustomer> mCustomer = root.join("customerId");

                return cb.like(mCustomer.get("familyName"), "%" + visitorName + "%");
            }
        };
    }
    /**
     * 企業名でイベントテンポラリを検索する。中間一致。
     */
    public static Specification<TEventTmp> companyNameContains(String companyName) {
        return StringUtils.isEmpty(companyName) ? null : new Specification<TEventTmp>() {
            @Override
            public Predicate toPredicate(Root<TEventTmp> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

                Join<TEventTmp, MCustomer> mCustomer = root.join("customerId");
                Join<MCustomer, MBusinessConnection> mBusinessConnection = mCustomer.join("businessConnectionId");

                return cb.like(mBusinessConnection.get("businessConnectionName"), "%" + companyName + "%");
            }
        };
    }

}
