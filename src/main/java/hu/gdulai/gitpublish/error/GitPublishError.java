package hu.gdulai.gitpublish.error;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/** @author gdulai */
public class GitPublishError extends Throwable {
  private static final long serialVersionUID = 1L;

  @Nonnull private final String id;
  @Nonnull private final String message;
  @Nullable private final Exception cause;

  public GitPublishError(@Nonnull final String id, @Nonnull final String message) {
    this(id, message, null);
  }

  public GitPublishError(
      @Nonnull final String id, @Nonnull final String message, @Nullable final Exception cause) {
    this.id = id;
    this.message = message;
    this.cause = cause;
  }

  @Override
  public String getMessage() {
    return message;
  }

  @Override
  public synchronized Throwable getCause() {
    return cause;
  }

  @Override
  public String toString() {
    return "[" + id + "]" + " " + message + " [" + cause.toString() + "]";
  }
}
