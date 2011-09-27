/*
 * VCS.java
 *
 * Copyright (C) 2009-11 by RStudio, Inc.
 *
 * This program is licensed to you under the terms of version 3 of the
 * GNU Affero General Public License. This program is distributed WITHOUT
 * ANY EXPRESS OR IMPLIED WARRANTY, INCLUDING THOSE OF NON-INFRINGEMENT,
 * MERCHANTABILITY OR FITNESS FOR A PARTICULAR PURPOSE. Please refer to the
 * AGPL (http://www.gnu.org/licenses/agpl-3.0.txt) for more details.
 *
 */
package org.rstudio.studio.client.workbench.views.vcs;

import com.google.gwt.core.client.JsArray;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.Provider;
import org.rstudio.core.client.command.CommandBinder;
import org.rstudio.core.client.command.Handler;
import org.rstudio.core.client.widget.Operation;
import org.rstudio.core.client.widget.ProgressIndicator;
import org.rstudio.core.client.widget.ProgressOperationWithInput;
import org.rstudio.studio.client.application.events.EventBus;
import org.rstudio.studio.client.common.GlobalDisplay;
import org.rstudio.studio.client.common.SimpleRequestCallback;
import org.rstudio.studio.client.common.console.ConsoleProcess;
import org.rstudio.studio.client.common.vcs.StatusAndPath;
import org.rstudio.studio.client.common.vcs.VCSServerOperations;
import org.rstudio.studio.client.server.Void;
import org.rstudio.studio.client.server.VoidServerRequestCallback;
import org.rstudio.studio.client.workbench.WorkbenchView;
import org.rstudio.studio.client.workbench.commands.Commands;
import org.rstudio.studio.client.workbench.views.BasePresenter;
import org.rstudio.studio.client.workbench.views.vcs.events.AskPassEvent;
import org.rstudio.studio.client.workbench.views.vcs.events.VcsRefreshEvent;
import org.rstudio.studio.client.workbench.views.vcs.events.VcsRefreshHandler;
import org.rstudio.studio.client.workbench.views.vcs.frame.VCSPopup;
import org.rstudio.studio.client.workbench.views.vcs.dialog.HistoryPresenter;
import org.rstudio.studio.client.workbench.views.vcs.dialog.ReviewPresenter;
import org.rstudio.studio.client.workbench.views.vcs.model.VcsState;

import java.util.ArrayList;

// TODO: Pull/push results should be shown in a dialog, even on success

public class VCS extends BasePresenter implements IsWidget
{
   public interface Binder extends CommandBinder<Commands, VCS> {}

   public interface Display extends WorkbenchView, IsWidget
   {
      void setItems(ArrayList<StatusAndPath> items);
      ArrayList<String> getSelectedPaths();

      void showProgress();
   }

   @Inject
   public VCS(Display view,
              Provider<ReviewPresenter> pReviewPresenter,
              Provider<HistoryPresenter> pHistoryPresenter,
              VCSServerOperations server,
              Commands commands,
              Binder commandBinder,
              VcsState vcsState,
              EventBus events,
              final GlobalDisplay globalDisplay)
   {
      super(view);
      view_ = view;
      pReviewPresenter_ = pReviewPresenter;
      pHistoryPresenter_ = pHistoryPresenter;
      server_ = server;
      vcsState_ = vcsState;

      commandBinder.bind(commands, this);

      view_.setProgress(true);
      vcsState_.addVcsRefreshHandler(new VcsRefreshHandler()
      {
         @Override
         public void onVcsRefresh(VcsRefreshEvent event)
         {
            refresh();
         }
      });

      events.addHandler(AskPassEvent.TYPE, new org.rstudio.studio.client.workbench.views.vcs.events.AskPassEvent.Handler()
      {
         @Override
         public void onAskPass(final AskPassEvent e)
         {
            globalDisplay.promptForPassword(
                  "Password",
                  e.getPrompt(),
                  "",
                  new ProgressOperationWithInput<String>()
                  {
                     @Override
                     public void execute(String input,
                                         ProgressIndicator indicator)
                     {
                        indicator.onCompleted();
                        server_.askpassReturn(
                              e.getHandle(),
                              input,
                              new VoidServerRequestCallback(indicator));
                     }
                  },
                  new Operation()
                  {
                     @Override
                     public void execute()
                     {
                        server_.askpassReturn(e.getHandle(), null,
                                              new SimpleRequestCallback<Void>());
                     }
                  });
         }
      });
   }

   @Override
   public Widget asWidget()
   {
      return view_.asWidget();
   }

   @Handler
   void onVcsDiff()
   {
      showReviewPane(false);
   }

   private void showReviewPane(boolean showHistory)
   {
      VCSPopup.show(pReviewPresenter_.get(),
                    pHistoryPresenter_.get(),
                    showHistory);
   }

   @Handler
   void onVcsStage()
   {
      ArrayList<String> paths = view_.getSelectedPaths();
      if (paths.size() == 0)
         return;

      server_.vcsAdd(paths, new SimpleRequestCallback<Void>("Stage Changes"));
   }

   @Handler
   void onVcsUnstage()
   {
      ArrayList<String> paths = view_.getSelectedPaths();
      if (paths.size() == 0)
         return;

      server_.vcsUnstage(paths,
                         new SimpleRequestCallback<Void>("Unstage Changes"));
   }

   @Handler
   void onVcsRevert()
   {
      ArrayList<String> paths = view_.getSelectedPaths();
      if (paths.size() == 0)
         return;

      server_.vcsRevert(paths, new SimpleRequestCallback<Void>("Revert Changes"));
   }

   @Handler
   void onVcsCommit()
   {
      showReviewPane(false);
   }

   @Handler
   void onVcsRefresh()
   {
      view_.showProgress();
      vcsState_.refresh();
   }

   @Handler
   void onVcsShowHistory()
   {
      showReviewPane(true);
   }

   @Handler
   void onVcsPull()
   {
      server_.vcsPull(new SimpleRequestCallback<ConsoleProcess>() {
         @Override
         public void onResponseReceived(ConsoleProcess proc)
         {
            new ConsoleProgressDialog("Pull", proc).showModal();
         }
      });
   }

   @Handler
   void onVcsPush()
   {
      server_.vcsPush(new SimpleRequestCallback<ConsoleProcess>() {
         @Override
         public void onResponseReceived(ConsoleProcess proc)
         {
            new ConsoleProgressDialog("Push", proc).showModal();
         }
      });
   }

   private void refresh()
   {
      JsArray<StatusAndPath> status = vcsState_.getStatus();
      ArrayList<StatusAndPath> list = new ArrayList<StatusAndPath>();
      for (int i = 0; i < status.length(); i++)
         list.add(status.get(i));
      view_.setItems(list);
   }

   private final Display view_;
   private final Provider<ReviewPresenter> pReviewPresenter_;
   private final Provider<HistoryPresenter> pHistoryPresenter_;
   private final VCSServerOperations server_;
   private final VcsState vcsState_;
}
