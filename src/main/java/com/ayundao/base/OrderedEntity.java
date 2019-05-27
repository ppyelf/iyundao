package com.ayundao.base;

import org.apache.commons.lang.builder.CompareToBuilder;
import org.hibernate.search.annotations.*;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.Min;
import java.io.Serializable;

/**
 * @ClassName: OrderedEntity
 * @project: ayundao
 * @author: 念
 * @Date: 2019/5/13 16:24
 * @Description: Entity - 排序基类
 * @Version: V1.0
 */
@MappedSuperclass
public abstract class OrderedEntity<ID extends Serializable> extends BaseEntity<ID> implements Comparable<OrderedEntity<ID>> {

	/**
	 * "排序"属性名称
	 */
	public static final String ORDER_PROPERTY_NAME = "order";
	private static final long serialVersionUID = 5995013015967525827L;
	/**
	 * 排序
	 */
	@Field(store = Store.YES, index = Index.YES, analyze = Analyze.NO)
	@NumericField
	@Min(0)
	@Column(name = "orders")
	private Integer order;

	/**
	 * 获取排序
	 * 
	 * @return 排序
	 */
	public Integer getOrder() {
		return order;
	}

	/**
	 * 设置排序
	 * 
	 * @param order
	 *            排序
	 */
	public void setOrder(Integer order) {
		this.order = order;
	}

	/**
	 * 实现compareTo方法
	 * 
	 * @param orderEntity
	 *            排序对象
	 * @return 比较结果
	 */
	public int compareTo(OrderedEntity<ID> orderEntity) {
		if (orderEntity == null) {
			return 1;
		}
		return new CompareToBuilder().append(getOrder(), orderEntity.getOrder()).append(getId(), orderEntity.getId()).toComparison();
	}

}