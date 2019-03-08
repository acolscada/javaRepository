package cn.acol.scada.domain;


import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Data;
@Entity
@Data
public class SeasonPrice {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private Date SeasonPriceStart; 
	private Date seasonPriceEnd;
	private float price;
	
	@ManyToOne
	@JoinColumn(name="pricePlan")
	private PricePlan pricePlan;
}
