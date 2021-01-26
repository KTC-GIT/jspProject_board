package dbControl;

import java.util.ArrayList;
import java.util.List;

import dbVo.BoardVo;
import dbVo.ReplyVo;

public class Paging {
	private int idxCount = 0;			//list를 받아서 그 length값을 받는다.
	public int pagingLimit = 20;		//한페이지에 들어갈 양.
	private int lastPage = 0;			//'idxCount/pagingNum'을 해서 나온수에 'idxCount%pagingNum'해서 '0'이 아니면 '+1'을 한다.
	public int pageListLimit = 5;
	public int listTotal = 0;
	
	BoardDao dao = BoardDao.getInstance();
	
	public Paging() {}
	
	public int getIdxCount(List<BoardVo> list) {
		
		idxCount = list.size();
		
		return idxCount;
	}
	
	public int getRidxCount(List<ReplyVo> rList) {
		idxCount = rList.size();
		
		return idxCount;
	}
	
	public int getLastPage() {
		int tmp = idxCount%pagingLimit;
		
		if(tmp != 0) {
			lastPage = idxCount/pagingLimit + 1;
		}
		else {
			lastPage = idxCount/pagingLimit;
		}
		
		return lastPage;
	}
	public int getListTotal(int lastPage,int pageListLimit) {
		int tmp = lastPage%pageListLimit;
		if(tmp != 0) {
			listTotal = lastPage/pageListLimit +1;
		}
		else {
			listTotal = lastPage/pageListLimit;
		}
		
		return listTotal;
	}
	
	
//	public List<BoardVo> splitPage(List<BoardVo> list,int pageCount) {			//처음에 20보다 적은 숫자의 게시물이 있는지모르고 잘못 설정해서 java.lang.IndexOutOfBoundsException: Index: 9, Size: 9 가 떳었다..
//		List<BoardVo> spList= new ArrayList<>();
//		int count = this.getIdxCount(list);
//		
//		if((pageCount>1) && (pageCount*pagingLimit>count)) {
//			for(int i=((pageCount-1)*pagingLimit)+1;i<=count;i++) {
//				BoardVo vo = new BoardVo();
//				vo = list.get(i-1);
//				
//				spList.add(vo);
//			}
//		}
//		else if((pageCount>1) && (pageCount*pagingLimit<count)) {
//			for(int i=((pageCount-1)*pagingLimit)+1;i<=pageCount*pagingLimit;i++) {
//				BoardVo vo = new BoardVo();
//				vo = list.get(i-1);
//				
//				spList.add(vo);
//			}
//		}
//		else if(pageCount*pagingLimit>count) {
//			for(int i=1;i<=count%pagingLimit;i++) {
//				BoardVo vo = new BoardVo();
//				vo = list.get(i-1);
//				
//				spList.add(vo);
//			}
//		}
//		else {
//			for(int i=pageCount;i<=pageCount*pagingLimit;i++) {
//				BoardVo vo = new BoardVo();
//				vo = list.get(i-1);
//				
//				spList.add(vo);
//			}
//		}
//		
//		return spList;
//	}
	
	public List<ReplyVo> splitRpage(List<ReplyVo> rList,int pageCount) {			//처음에 20보다 적은 숫자의 게시물이 있는지모르고 잘못 설정해서 java.lang.IndexOutOfBoundsException: Index: 9, Size: 9 가 떳었다..
		List<ReplyVo> sprList= new ArrayList<>();
		int count = this.getRidxCount(rList);
		
		if((pageCount>1) && (pageCount*pagingLimit>count)) {
			for(int i=((pageCount-1)*pagingLimit)+1;i<=count;i++) {
				ReplyVo vo = new ReplyVo();
				vo = rList.get(i-1);
				
				sprList.add(vo);
			}
		}
		else if((pageCount>1) && (pageCount*pagingLimit<count)) {
			for(int i=((pageCount-1)*pagingLimit)+1;i<=pageCount*pagingLimit;i++) {
				ReplyVo vo = new ReplyVo();
				vo = rList.get(i-1);
				
				sprList.add(vo);
			}
		}
		else if(pageCount*pagingLimit>count) {
			for(int i=1;i<=count%pagingLimit;i++) {
				ReplyVo vo = new ReplyVo();
				vo = rList.get(i-1);
				
				sprList.add(vo);
			}
		}
		else {
			for(int i=pageCount;i<=pageCount*pagingLimit;i++) {
				ReplyVo vo = new ReplyVo();
				vo = rList.get(i-1);
				
				sprList.add(vo);
			}
		}
		
		return sprList;
	}
	
	
	
}
