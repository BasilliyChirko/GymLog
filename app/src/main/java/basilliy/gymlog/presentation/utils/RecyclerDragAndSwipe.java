package basilliy.gymlog.presentation.utils;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView.*;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import java.util.Collections;
import java.util.List;

public class RecyclerDragAndSwipe extends ItemTouchHelper.Callback {

    private boolean isVerticalOrientation = true;
    private boolean drag = false;
    private boolean swipe = false;

    public static void enable(RecyclerView list, Adapter adapter, boolean drag, boolean swipe) {
        RecyclerDragAndSwipe callback = new RecyclerDragAndSwipe(adapter);
        callback.drag = drag;
        callback.swipe = swipe;

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);

        LayoutManager layoutManager = list.getLayoutManager();
        if (layoutManager instanceof LinearLayoutManager) {
            LinearLayoutManager llm = (LinearLayoutManager) layoutManager;
            callback.isVerticalOrientation = llm.getOrientation() != LinearLayoutManager.HORIZONTAL;
        }

        itemTouchHelper.attachToRecyclerView(list);
    }

    public static void swapData(List data, int fromPosition, int toPosition) {
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(data, i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(data, i, i - 1);
            }
        }
    }

    private final Adapter mAdapter;

    private RecyclerDragAndSwipe(Adapter adapter) {
        mAdapter = adapter;
    }

    @Override
    public boolean isLongPressDragEnabled() {
        return drag;
    }

    @Override
    public boolean isItemViewSwipeEnabled() {
        return swipe;
    }

    @Override
    public void onSwiped(ViewHolder viewHolder, int direction) {
        mAdapter.onItemDismiss(viewHolder.getAdapterPosition());
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, ViewHolder viewHolder) {
        int dragFlags;
        int swipeFlags;

        if (isVerticalOrientation) {
            dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
            swipeFlags = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
        } else {
            swipeFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
            dragFlags = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
        }

        return makeMovementFlags(dragFlags, swipeFlags);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, ViewHolder viewHolder, ViewHolder target) {
        mAdapter.onItemMove(viewHolder.getAdapterPosition(), target.getAdapterPosition());
        return true;
    }

    public interface Adapter {
        void onItemMove(int fromPosition, int toPosition);
        void onItemDismiss(int position);
    }

}
