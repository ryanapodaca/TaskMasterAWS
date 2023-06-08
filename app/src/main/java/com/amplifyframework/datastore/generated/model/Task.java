package com.amplifyframework.datastore.generated.model;

import com.amplifyframework.core.model.temporal.Temporal;
import com.amplifyframework.core.model.annotations.BelongsTo;

import java.util.List;
import java.util.UUID;
import java.util.Objects;

import androidx.core.util.ObjectsCompat;

import com.amplifyframework.core.model.AuthStrategy;
import com.amplifyframework.core.model.Model;
import com.amplifyframework.core.model.ModelOperation;
import com.amplifyframework.core.model.annotations.AuthRule;
import com.amplifyframework.core.model.annotations.Index;
import com.amplifyframework.core.model.annotations.ModelConfig;
import com.amplifyframework.core.model.annotations.ModelField;
import com.amplifyframework.core.model.query.predicate.QueryField;

import static com.amplifyframework.core.model.query.predicate.QueryField.field;

/** This is an auto generated class representing the Task type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "Tasks", type = Model.Type.USER, version = 1, authRules = {
  @AuthRule(allow = AuthStrategy.PUBLIC, operations = { ModelOperation.CREATE, ModelOperation.UPDATE, ModelOperation.DELETE, ModelOperation.READ })
})
@Index(name = "byTeam", fields = {"teamID","title"})
public final class Task implements Model {
  public static final QueryField ID = field("Task", "id");
  public static final QueryField TITLE = field("Task", "title");
  public static final QueryField BODY = field("Task", "body");
  public static final QueryField DATE = field("Task", "date");
  public static final QueryField STATE = field("Task", "state");
  public static final QueryField TEAM_TITLE = field("Task", "teamID");
  public static final QueryField S3_KEY = field("Task", "s3Key");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="String", isRequired = true) String title;
  private final @ModelField(targetType="String", isRequired = true) String body;
  private final @ModelField(targetType="AWSDateTime") Temporal.DateTime date;
  private final @ModelField(targetType="taskStateEnum") TaskStateEnum state;
  private final @ModelField(targetType="Team") @BelongsTo(targetName = "teamID", targetNames = {"teamID"}, type = Team.class) Team teamTitle;
  private final @ModelField(targetType="String") String s3Key;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime createdAt;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime updatedAt;
  public String resolveIdentifier() {
    return id;
  }
  
  public String getId() {
      return id;
  }
  
  public String getTitle() {
      return title;
  }
  
  public String getBody() {
      return body;
  }
  
  public Temporal.DateTime getDate() {
      return date;
  }
  
  public TaskStateEnum getState() {
      return state;
  }
  
  public Team getTeamTitle() {
      return teamTitle;
  }
  
  public String getS3Key() {
      return s3Key;
  }
  
  public Temporal.DateTime getCreatedAt() {
      return createdAt;
  }
  
  public Temporal.DateTime getUpdatedAt() {
      return updatedAt;
  }
  
  private Task(String id, String title, String body, Temporal.DateTime date, TaskStateEnum state, Team teamTitle, String s3Key) {
    this.id = id;
    this.title = title;
    this.body = body;
    this.date = date;
    this.state = state;
    this.teamTitle = teamTitle;
    this.s3Key = s3Key;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      Task task = (Task) obj;
      return ObjectsCompat.equals(getId(), task.getId()) &&
              ObjectsCompat.equals(getTitle(), task.getTitle()) &&
              ObjectsCompat.equals(getBody(), task.getBody()) &&
              ObjectsCompat.equals(getDate(), task.getDate()) &&
              ObjectsCompat.equals(getState(), task.getState()) &&
              ObjectsCompat.equals(getTeamTitle(), task.getTeamTitle()) &&
              ObjectsCompat.equals(getS3Key(), task.getS3Key()) &&
              ObjectsCompat.equals(getCreatedAt(), task.getCreatedAt()) &&
              ObjectsCompat.equals(getUpdatedAt(), task.getUpdatedAt());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getTitle())
      .append(getBody())
      .append(getDate())
      .append(getState())
      .append(getTeamTitle())
      .append(getS3Key())
      .append(getCreatedAt())
      .append(getUpdatedAt())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("Task {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("title=" + String.valueOf(getTitle()) + ", ")
      .append("body=" + String.valueOf(getBody()) + ", ")
      .append("date=" + String.valueOf(getDate()) + ", ")
      .append("state=" + String.valueOf(getState()) + ", ")
      .append("teamTitle=" + String.valueOf(getTeamTitle()) + ", ")
      .append("s3Key=" + String.valueOf(getS3Key()) + ", ")
      .append("createdAt=" + String.valueOf(getCreatedAt()) + ", ")
      .append("updatedAt=" + String.valueOf(getUpdatedAt()))
      .append("}")
      .toString();
  }
  
  public static TitleStep builder() {
      return new Builder();
  }
  
  /**
   * WARNING: This method should not be used to build an instance of this object for a CREATE mutation.
   * This is a convenience method to return an instance of the object with only its ID populated
   * to be used in the context of a parameter in a delete mutation or referencing a foreign key
   * in a relationship.
   * @param id the id of the existing item this instance will represent
   * @return an instance of this model with only ID populated
   */
  public static Task justId(String id) {
    return new Task(
      id,
      null,
      null,
      null,
      null,
      null,
      null
    );
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(id,
      title,
      body,
      date,
      state,
      teamTitle,
      s3Key);
  }
  public interface TitleStep {
    BodyStep title(String title);
  }
  

  public interface BodyStep {
    BuildStep body(String body);
  }
  

  public interface BuildStep {
    Task build();
    BuildStep id(String id);
    BuildStep date(Temporal.DateTime date);
    BuildStep state(TaskStateEnum state);
    BuildStep teamTitle(Team teamTitle);
    BuildStep s3Key(String s3Key);
  }
  

  public static class Builder implements TitleStep, BodyStep, BuildStep {
    private String id;
    private String title;
    private String body;
    private Temporal.DateTime date;
    private TaskStateEnum state;
    private Team teamTitle;
    private String s3Key;
    @Override
     public Task build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new Task(
          id,
          title,
          body,
          date,
          state,
          teamTitle,
          s3Key);
    }
    
    @Override
     public BodyStep title(String title) {
        Objects.requireNonNull(title);
        this.title = title;
        return this;
    }
    
    @Override
     public BuildStep body(String body) {
        Objects.requireNonNull(body);
        this.body = body;
        return this;
    }
    
    @Override
     public BuildStep date(Temporal.DateTime date) {
        this.date = date;
        return this;
    }
    
    @Override
     public BuildStep state(TaskStateEnum state) {
        this.state = state;
        return this;
    }
    
    @Override
     public BuildStep teamTitle(Team teamTitle) {
        this.teamTitle = teamTitle;
        return this;
    }
    
    @Override
     public BuildStep s3Key(String s3Key) {
        this.s3Key = s3Key;
        return this;
    }
    
    /**
     * @param id id
     * @return Current Builder instance, for fluent method chaining
     */
    public BuildStep id(String id) {
        this.id = id;
        return this;
    }
  }
  

  public final class CopyOfBuilder extends Builder {
    private CopyOfBuilder(String id, String title, String body, Temporal.DateTime date, TaskStateEnum state, Team teamTitle, String s3Key) {
      super.id(id);
      super.title(title)
        .body(body)
        .date(date)
        .state(state)
        .teamTitle(teamTitle)
        .s3Key(s3Key);
    }
    
    @Override
     public CopyOfBuilder title(String title) {
      return (CopyOfBuilder) super.title(title);
    }
    
    @Override
     public CopyOfBuilder body(String body) {
      return (CopyOfBuilder) super.body(body);
    }
    
    @Override
     public CopyOfBuilder date(Temporal.DateTime date) {
      return (CopyOfBuilder) super.date(date);
    }
    
    @Override
     public CopyOfBuilder state(TaskStateEnum state) {
      return (CopyOfBuilder) super.state(state);
    }
    
    @Override
     public CopyOfBuilder teamTitle(Team teamTitle) {
      return (CopyOfBuilder) super.teamTitle(teamTitle);
    }
    
    @Override
     public CopyOfBuilder s3Key(String s3Key) {
      return (CopyOfBuilder) super.s3Key(s3Key);
    }
  }
  
}
