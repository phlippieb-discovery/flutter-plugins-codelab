public class PluginCodelabPlugin implements FlutterPlugin, MethodCallHandler {
  private MethodChannel channel;
  private Synth synth;
  private static final String channelName = "plugin_codelab";

  private static void setup(PluginCodelabPlugin plugin,
                            BinaryMessenger binaryMessenger) {
    plugin.channel = new MethodChannel(binaryMessenger, channelName);
    plugin.channel.setMethodCallHandler(plugin);
    plugin.synth = new Synth();
    plugin.synth.start();
  }

  @Override
  public void
  onAttachedToEngine(@NonNull FlutterPluginBinding flutterPluginBinding) {
    setup(this, flutterPluginBinding.getBinaryMessenger());
  }

  @Override
  public void onMethodCall(@NonNull MethodCall call, @NonNull Result result) {
    if (call.method.equals("getPlatformVersion")) {
      result.success("Android " + android.os.Build.VERSION.RELEASE);
    } else if (call.method.equals("onKeyDown")) {
      try {
        ArrayList arguments = (ArrayList)call.arguments;
        int numKeysDown = synth.keyDown((Integer)arguments.get(0));
        result.success(numKeysDown);
      } catch (Exception ex) {
        result.error("1", ex.getMessage(), ex.getStackTrace());
      }
    } else if (call.method.equals("onKeyUp")) {
      try {
        ArrayList arguments = (ArrayList)call.arguments;
        int numKeysDown = synth.keyUp((Integer)arguments.get(0));
        result.success(numKeysDown);
      } catch (Exception ex) {
        result.error("1", ex.getMessage(), ex.getStackTrace());
      }
    } else {
      result.notImplemented();
    }
  }
}
