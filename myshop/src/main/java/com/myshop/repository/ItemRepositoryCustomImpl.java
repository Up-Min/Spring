package com.myshop.repository;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.thymeleaf.util.StringUtils;

import com.myshop.constant.ItemSellStatus;
import com.myshop.dto.ItemSearchDto;
import com.myshop.dto.MainItemDto;
import com.myshop.dto.QMainItemDto;
import com.myshop.entity.Item;
import com.myshop.entity.QItem;
import com.myshop.entity.QItemImg;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQueryFactory;

public class ItemRepositoryCustomImpl implements ItemRepositoryCustom{
	
	// Repository에서 선언한 쿼리문을 좀 더 명확하게 사용할 수 있게 세팅해주는 클래스 java.
	// 아래 선언된 메소드들은 모두 'getAdminItemPage' 메소드에 사용된다.
	
	//쿼리 dsl을 쓰기위해 jpa 동적쿼리를 선언해준다.
	private JPAQueryFactory queryFactory;

	//의존성 주입을 생성자 방식으로 한다.
	public ItemRepositoryCustomImpl(EntityManager em) {
		this.queryFactory = new JPAQueryFactory(em);
	}
	
	// 기간을 산정해주는 메소드 - 동적쿼리를 만들어서 리턴해줄거다.
	private BooleanExpression regDtsAfter(String searchDateType) {
		// 현재 날짜, 시간 구하기
		LocalDateTime dateTime = LocalDateTime.now();
		
		if(StringUtils.equals("all", searchDateType) || searchDateType == null) return null; 
		// 문자열을 비교해주는 StringUtils. 전체기간이나, 입력 날짜 값이 없으면 null을 리턴.
		
		else if(StringUtils.equals("1d", searchDateType)) dateTime = dateTime.minusDays(1); // 하루 전 날짜를 출력해준다.
		else if(StringUtils.equals("1w", searchDateType)) dateTime = dateTime.minusWeeks(1); // 일주일 전 날짜를 출력해준다.
		else if(StringUtils.equals("1m", searchDateType)) dateTime = dateTime.minusMonths(1); // 한 달 전 날짜를 출력해준다.
		else if(StringUtils.equals("6m", searchDateType)) dateTime = dateTime.minusMonths(6); // 6개월 전 날짜를 출력해준다.
		
		return QItem.item.regTime.after(dateTime); 
		//등록날짜가 dateTime이후의 날짜를 해서 return 해준다. 
		// 23.1.20 오늘 기준,minusDays(1)인경우 23.1.19 이후의 time을 리턴해준다.
	}
	
	private BooleanExpression searchSellStatusEq(ItemSellStatus searchSellStatus) {
		// 삼항연산자를 써서 리턴해줄거임
		
		return searchSellStatus == null? null : QItem.item.itemSellStatus.eq(searchSellStatus);
		// Querydsl 환경만들면서 자동으로 생성한 QItem.java를 가져온다.
		// searchSellStatusr가 null인지 파악하고, null이면 null을 주고, 아니면 입력받은 searchSellStatus를 찾아주는 동적쿼리를 리턴한다.
	}
	
	private BooleanExpression searchByLike(String searchBy, String searchQuery) { 
		//매개변수가 2개가 있어야 한다. 상품명으로 검색? 등록자로 검색?
		// searchBy는 itemSearchDto.getSearchBy()가 들어온다.
		if(StringUtils.equals("itemNm", searchBy)) {
			return QItem.item.itemNm.like("%" + searchQuery +"%"); //itemNm LIKE % 청바지 %
		} else if(StringUtils.equals("createdBy", searchBy)) {
			return QItem.item.createdBy.like("%" + searchQuery +"%"); //created LIKE %test.com%
		}
		// 즉, 상품명으로 검색할지, 등록자로 검색할지를 선택하고, 입력받은 searchQuery를 like로 찾을 수 있게 해준다.
		return null;
	}
	
	
	@Override
	public Page<Item> getAdminItemPage(ItemSearchDto itemSearchDto, Pageable pageable) {
		List<Item> content = queryFactory
							.selectFrom(QItem.item) //select*from item
							.where(regDtsAfter(itemSearchDto.getSearchDateType()), // where reg_time = ?
									searchSellStatusEq(itemSearchDto.getSearchSellStatus()), // and sell_status = ?
									searchByLike(itemSearchDto.getSearchBy(), itemSearchDto.getSearchQuery())) // and itemNm LIKE %검색어%
							.orderBy(QItem.item.id.desc())
							.offset(pageable.getOffset()) //데이터를 가져올 시작 index.
							.limit(pageable.getPageSize()) //한번에 가져올 최대 갯수
							.fetch();
		 // 기간, 판매상태, 상품명 등 검색 조건을 추가함. 근데 갑자기 where 'reg_time=1d' 하면 안나오니까
		 // 각 컬럼별로 기간 산정을 해줄 수 있는 필터? 같은걸 해줘야한다. 
		 // 기간을 산정해서 날짜로 바꿔주는 메소드가 필요함. -> 날짜 리턴해주는 regDtsAfter
		
//		long total = content.size(); // 전체 레코드의 사이즈를 가져옴.
	
		// 토탈을 쿼리문으로 변경. 
		long total = queryFactory.select(Wildcard.count).from(QItem.item) // wildCart.count -> count(*)와 동일.
				.where(regDtsAfter(itemSearchDto.getSearchDateType()),
				searchSellStatusEq(itemSearchDto.getSearchSellStatus()),
				searchByLike(itemSearchDto.getSearchBy(), itemSearchDto.getSearchQuery()))
				.fetchOne(); // 조회 대상이 한건이면 반환, 이상이면 에러 발생 (4장 확인)
		
		return new PageImpl<>(content, pageable, total);

	}

	private BooleanExpression itemNmLike(String searchQuery) {
		return StringUtils.isEmpty(searchQuery) ? null : QItem.item.itemNm.like("%"+searchQuery+"%");
		// null이나 빈 문자열이 아니면 like 쿼리문을 사용시킴.
	}
	
	@Override
	public Page<MainItemDto> getMainItemPage(ItemSearchDto itemSearchDto, Pageable pageable) {
		QItem item = QItem.item; // 쿼리 dsl사용 위해 QItem 선언
		QItemImg itemImg = QItemImg.itemImg; 
		
		// 쿼리 dsl 사용 MainItemDto로 쿼리 조회한 결과를 바로 보내줄거임. (MainItemDto에서 선언한 @쿼리프로젝션 으로 QMainItemDto.java 만들어져있음) 
		//new MainItemDto로 바로 바꿔준다 (소괄호 안에 있는)
		List<MainItemDto> content = queryFactory.select(
				new QMainItemDto(item.id, item.itemNm, item.itemDetail, itemImg.imgUrl, item.price)) // select * from
				.from(itemImg).join(itemImg.item, item) //itemImg.item과 item엔티티를 join 시킨다.
				.where(itemImg.repimgYn.eq("Y"))
				.where(itemNmLike(itemSearchDto.getSearchQuery())) //itemNmLike 구현해줘야함.
				.orderBy(item.id.desc())
				.offset(pageable.getOffset())
				.limit(pageable.getPageSize())
				.fetch();
			
		// 전체 레코드 갯수 구하기
		long total = queryFactory.select(Wildcard.count) 
				.from(itemImg).join(itemImg.item, item) //itemImg.item과 item엔티티를 join 시킨다.
				.where(itemImg.repimgYn.eq("Y"))
				.where(itemNmLike(itemSearchDto.getSearchQuery())) //itemNmLike 구현해줘야함.
				.fetchOne();
		
		// 페이지를 리턴해준다.
		return new PageImpl<>(content, pageable, total); // 페이지 내용과, 페이징과, 전체 레코드 갯수만 넣으면 된다.
	}
	
	
	
	
}
