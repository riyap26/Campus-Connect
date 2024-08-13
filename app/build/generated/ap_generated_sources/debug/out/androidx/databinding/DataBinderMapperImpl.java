package androidx.databinding;

public class DataBinderMapperImpl extends MergedDataBinderMapper {
  DataBinderMapperImpl() {
    addMapper(new edu.gatech.campusconnect.DataBinderMapperImpl());
  }
}
