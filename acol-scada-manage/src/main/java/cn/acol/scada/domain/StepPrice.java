package cn.acol.scada.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Data;
@Entity
@Data
public class StepPrice {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private long useGas;
	private float price;
	
	@ManyToOne
	@JoinColumn(name="pricePlan")
	private PricePlan pricePlan;
}
