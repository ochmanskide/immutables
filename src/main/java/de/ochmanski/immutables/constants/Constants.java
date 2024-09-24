package de.ochmanski.immutables.constants;

import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

import static de.ochmanski.immutables.constants.Constants.Warning.UNUSED;

public interface Constants
{
  @NonNls
  @NotNull
  @Unmodifiable
  String BLANK = "";

  @SuppressWarnings(UNUSED)
  interface Language
  {
    @NonNls
    @NotNull
    @Unmodifiable
    String REGEX = "RegExp",
      JAVA = "Java",
      SQL = "SQL",
      POSTGRES = "PostgreSQL",
      HTML = "HTML",
      XML = "XML",
      XPATH = "XPath",
      JSON = "JSON",
      JSONPATH = "JSONPath",
      GROOVY = "Groovy";
  }

  /**
   * Below is a list of valid warning names available in the {@code @SuppressWarnings} annotation:
   * <ul>
   *   <li><b>all</b>: this is sort of a wildcard that suppresses all warnings</li>
   *   <li><b>boxing</b>: suppresses warnings related to boxing/unboxing operations</li>
   *   <li><b>unused</b>: suppresses warnings of unused code</li>
   *   <li><b>cast</b>: suppresses warnings related to object cast operations</li>
   *   <li><b>deprecation</b>: suppresses warnings related to deprecation, such as a deprecated class or method</li>
   *   <li><b>restriction</b>: suppresses warnings related to the usage of discouraged or forbidden references</li>
   *   <li><b>dep-ann</b>: suppresses warnings relative to deprecated annotations</li>
   *   <li><b>fallthrough</b>: suppresses warnings related to missing break statements in switch statements</li>
   *   <li><b>finally</b>: suppresses warnings related to finally blocks that don’t return</li>
   *   <li><b>hiding</b>: suppresses warnings relative to locals that hide variables</li>
   *   <li><b>incomplete-switch</b>: suppresses warnings relative to missing entries in a switch statement (enum case)</li>
   *   <li><b>nls</b>: suppresses warnings related to non-nls string literals</li>
   *   <li><b>null</b>: suppresses warnings related to null analysis</li>
   *   <li><b>serial</b>: suppresses warnings related to the missing serialVersionUID field, which is typically found in a Serializable class</li>
   *   <li><b>static-access</b>: suppresses warnings related to incorrect static variable access</li>
   *   <li><b>synthetic-access</b>: suppresses warnings related to unoptimized access from inner classes</li>
   *   <li><b>unchecked</b>: suppresses warnings related to unchecked operations</li>
   *   <li><b>unqualified-field-access</b>: suppresses warnings related to unqualified field access</li>
   *   <li><b>javadoc</b>: suppresses warnings related to Javadoc</li>
   *   <li><b>rawtypes</b>: suppresses warnings related to the usage of raw types</li>
   *   <li><b>resource</b>: suppresses warnings related to the usage of resources of type Closeable</li>
   *   <li><b>super</b>: suppresses warnings related to overriding a method without super invocations</li>
   *   <li><b>sync-override</b>: suppresses warnings due to missing synchronized when overriding a synchronized method</li>
   * </ul>
   */
  @SuppressWarnings(UNUSED)
  interface Warning
  {
    /**
     * this is sort of a wildcard that suppresses all warnings.
     */
    @NonNls
    String ALL = "all";

    /**
     * suppresses warnings related to properties that are not mandatory, and have default values provided.
     */
    @NonNls
    String PROPERTY_NOT_FOUND = "UnresolvedPropertyKey";

    @NonNls
    String UNSTABLE_API_USAGE = "UnstableApiUsage";

    @NonNls
    String NOT_NULL_FIELD_NOT_INITIALIZED = "NotNullFieldNotInitialized";

    @NonNls
    String UNUSED_RETURN_VALUE = "UnusedReturnValue";

    @NonNls
    String DATA_FLOW_ISSUE = "DataFlowIssue";

    @NonNls
    String CDI_INJECTION_POINTS_INSPECTION = "CdiInjectionPointsInspection";

    @NonNls
    String NULLABLE_PROBLEMS = "NullableProblems";

    @NonNls
    String BLOCKING_METHOD_IN_NON_BLOCKING_CONTEXT = "BlockingMethodInNonBlockingContext";

    @NonNls
    String NULLNESS_ASSIGNMENT = "nullness:assignment";

    @NonNls
    String PROHIBITED_EXCEPTION_THROWN = "ProhibitedExceptionThrown";

    @NonNls
    String MAGIC_CHARACTER = "MagicCharacter";

    @NonNls
    String ACTUAL_VALUE_OF_PARAMETER_IS_ALWAYS_THE_SAME = "SameParameterValue";

    @NonNls
    String SAME_PARAMETER_VALUE = ACTUAL_VALUE_OF_PARAMETER_IS_ALWAYS_THE_SAME;

    @NonNls
    String STRING_EQUALITY = "StringEquality";

    @NonNls
    String BUSY_WAIT = "BusyWait";

    @NonNls
    String MISMATCHED_QUERY_AND_UPDATE_OF_COLLECTION = "MismatchedQueryAndUpdateOfCollection";

    @NonNls
    String DUPLICATED_CODE = "DuplicatedCode";

    @NonNls
    String LAMBDA_PARAMETER_TYPE_CAN_BE_SPECIFIED = "LambdaParameterTypeCanBeSpecified";

    @NonNls
    String FIELD_CAN_BE_LOCAL = "FieldCanBeLocal";

    @NonNls
    String PATTERN_VALIDATION = "PatternValidation";

    @NonNls
    String REDUNDANT_SUPPRESSION = "RedundantSuppression";

    @NonNls
    String REDUNDANT_METHOD_OVERRIDE = "RedundantMethodOverride";

    @NonNls
    String UNRESOLVED_PROPERTY_KEY = "UnresolvedPropertyKey";

    @NonNls
    String FUNCTIONAL_EXPRESSION_CAN_BE_FOLDED = "FunctionalExpressionCanBeFolded";

    @NonNls
    String SPELL_CHECKING_INSPECTION = "SpellCheckingInspection";

    @NonNls
    String SIMPLIFY_STREAM_API_CALL_CHAINS = "SimplifyStreamApiCallChains";

    @NonNls
    String CONSTANT_VALUE = "ConstantValue";

    @NonNls
    String PROHIBITED_EXCEPTION_CAUGHT = "ProhibitedExceptionCaught";

    /**
     * <b>boxing</b>: suppresses warnings related to boxing/unboxing operations
     */
    @NonNls
    String BOXING = "boxing";

    /**
     * <b>unused</b>: suppresses warnings of unused code
     */
    @NonNls
    String UNUSED = "unused";

    /**
     * <b>cast</b>: suppresses warnings related to object cast operations
     */
    @NonNls
    String CAST = "cast";

    /**
     * <b>deprecation</b>: suppresses warnings related to deprecation, such as a deprecated class or method
     */
    @NonNls
    String DEPRECATION = "deprecation";

    /**
     * <b>restriction</b>: suppresses warnings related to the usage of discouraged or forbidden references
     */
    @NonNls
    String RESTRICTION = "restriction";

    /**
     * <b>dep-ann</b>: suppresses warnings relative to deprecated annotations
     */
    @NonNls
    String DEP_ANN = "dep-ann";

    /**
     * <b>fallthrough</b>: suppresses warnings related to missing break statements in switch statements
     */
    @NonNls
    String FALLTHROUGH = "fallthrough";

    /**
     * <b>finally</b>: suppresses warnings related to finally blocks that don’t return
     */
    @NonNls
    String FINALLY = "finally";

    /**
     * <b>hiding</b>: suppresses warnings relative to locals that hide variables
     */
    @NonNls
    String HIDING = "hiding";

    /**
     * <b>incomplete-switch</b>: suppresses warnings relative to missing entries in a switch statement (enum
     * case)
     */
    @NonNls
    String INCOMPLETE_SWITCH = "incomplete-switch";

    /**
     * <b>nls</b>: suppresses warnings related to non-nls string literals
     */
    @NonNls
    String NLS = "nls";

    /**
     * <b>null</b>: suppresses warnings related to null analysis
     */
    @NonNls
    String NULL = "null";

    /**
     * <b>serial</b>: suppresses warnings related to the missing serialVersionUID field, which is typically found
     * in
     * a Serializable class
     */
    @NonNls
    String SERIAL = "serial";

    /**
     * <b>static-access</b>: suppresses warnings related to incorrect static variable access
     */
    @NonNls
    String STATIC_ACCESS = "static-access";

    /**
     * <b>synthetic-access</b>: suppresses warnings related to unoptimized access from inner classes
     */
    @NonNls
    String SYNTHETIC_ACCESS = "synthetic-access";

    /**
     * <b>unchecked</b>: suppresses warnings related to unchecked operations
     */
    @NonNls
    String UNCHECKED = "unchecked";

    /**
     * <b>unqualified-field-access</b>: suppresses warnings related to unqualified field access
     */
    @NonNls
    String UNQUALIFIED_FIELD_ACCESS = "unqualified-field-access";

    /**
     * <b>javadoc</b>: suppresses warnings related to Javadoc
     */
    @NonNls
    String JAVADOC = "javadoc";

    /**
     * <b>rawtypes</b>: suppresses warnings related to the usage of raw types
     */
    @NonNls
    String RAWTYPES = "rawtypes";

    /**
     * <b>resource</b>: suppresses warnings related to the usage of resources of type Closeable
     */
    @NonNls
    String RESOURCE = "resource";

    /**
     * <b>super</b>: suppresses warnings related to overriding a method without super invocations
     */
    @NonNls
    String SUPER = "super";

    /**
     * <b>sync-override</b>: suppresses warnings due to missing synchronized when overriding a synchronized
     * method
     */
    @NonNls
    String SYNC_OVERRIDE = "sync-override";
  }
}
