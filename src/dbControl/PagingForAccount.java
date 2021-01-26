package dbControl;

import java.util.List;

import dbVo.AccountVo;

public class PagingForAccount {
	public int totalIdx=0;
	public int idxLimit = 5;
	public int nowPage=1;
	public int startIdx = 0+(idxLimit*(nowPage-1));
	public int pageLimit = 5;
	public int pageListCount = 1;
	public int lastPage=1;
	
	public PagingForAccount() {}
	
	public int getTotalIdx(List<AccountVo> list) {
		this.totalIdx = list.size();
		return this.totalIdx;
	}
	public int getLastPage(int totalIdx) {
		int lastPage = totalIdx%idxLimit==0?totalIdx/idxLimit:(totalIdx/idxLimit)+1;
		this.lastPage = lastPage;
		return this.lastPage;
	}
}
