package com.ood.waterball.teampathy.Fragments.Forum;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ood.waterball.teampathy.Controllers.Global;
import com.ood.waterball.teampathy.Domains.Issue;
import com.ood.waterball.teampathy.Domains.IssueComment;
import com.ood.waterball.teampathy.Fragments.ActivityBaseFragment;
import com.ood.waterball.teampathy.R;

import java.util.List;


public class IssueDetailsFragment extends ActivityBaseFragment {
    private Issue currentIssue;
    private List<IssueComment> issueCommentList;

    private ImageView posterHeadImg;
    private TextView issueTitleTxt;
    private TextView issueContentTxt;
    private TextView dateTxt;
    private TextView issueTypeTxt;
    private RecyclerView commentsRecyclerView;
    private LinearLayoutManager layoutManager;
    private MyRecyclerAdapter recyclerAdapter;

    public static IssueDetailsFragment getInstance(Issue currentIssue){
        IssueDetailsFragment fragment = new IssueDetailsFragment();
        Bundle args = new Bundle();
        args.putSerializable("issue",currentIssue);
        fragment.setArguments(args);
        return fragment;
    }


    public IssueDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    protected void onFetchData(@Nullable Bundle savedInstanceState, @Nullable Bundle arguBundle) {
        try {
            currentIssue = (Issue) arguBundle.getSerializable("issue");
            issueCommentList = Global.getTeamPathyFacade().getIssueCommentListByIssueId(currentIssue.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_issue_details_page;
    }

    @Override
    protected void onFindViews(View parentView) {
        issueTitleTxt = (TextView) parentView.findViewById(R.id.title_issue_details);
        posterHeadImg = (ImageView) parentView.findViewById(R.id.poster_picture_issue_details);
        issueContentTxt = (TextView) parentView.findViewById(R.id.issue_content_issue_details);
        dateTxt = (TextView) parentView.findViewById(R.id.postdate_issue_details);
        issueTypeTxt = (TextView) parentView.findViewById(R.id.issuetype_issue_details);
        commentsRecyclerView = (RecyclerView) parentView.findViewById(R.id.recycler_issue_details);
    }

    @Override
    protected void onControlViews() {
        setupViews();
        initiateRecyclerView();
    }

    private void setupViews(){
        //todo Glide 載入
        Glide.with(this).load(R.drawable.zongye).into(posterHeadImg);
        issueContentTxt.setText(currentIssue.getContent());
        issueTypeTxt.setText(currentIssue.getType());
        issueTitleTxt.setText(currentIssue.getTitle());
        dateTxt.setText(currentIssue.getDateString());
    }

    private void initiateRecyclerView(){
        layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerAdapter = new MyRecyclerAdapter();
        commentsRecyclerView.setLayoutManager(layoutManager);
        commentsRecyclerView.setAdapter(recyclerAdapter);
        commentsRecyclerView.setNestedScrollingEnabled(false);  //改善 scrollview 滑動慢的問題 see : http://stackoverflow.com/questions/33143485/recyclerview-inside-scrollview-not-scrolling-smoothly
    }


    class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.ViewHolder>{

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(getContext()).inflate(R.layout.issue_details_item,parent,false));
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.authorTxt.setText(issueCommentList.get(position).getPoster().getName());
            holder.commentTxt.setText(issueCommentList.get(position).getContent());
            holder.dateTxt.setText(Global.getTeamPathyFacade().
                    convertDateToString(issueCommentList.get(position).getPostDate()));
        }

        @Override
        public int getItemCount() {
            return issueCommentList.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder{
            private TextView commentTxt;
            private TextView authorTxt;
            private TextView dateTxt;

            public ViewHolder(View itemView) {
                super(itemView);
                commentTxt = (TextView) itemView.findViewById(R.id.comment_text_issue_details_item);
                authorTxt = (TextView) itemView.findViewById(R.id.author_text_issue_details_item);
                dateTxt = (TextView) itemView.findViewById(R.id.datetime_text_issue_details_item);
            }
        }
    }
}