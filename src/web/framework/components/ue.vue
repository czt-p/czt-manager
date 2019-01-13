<template>
    <div class="edit_container">
        <quill-editor 
            v-model="addObj.content" 
            ref="myQuillEditor" 
            :options="editorOption" 
            @blur="onEditorBlur($event)" @focus="onEditorFocus($event)"
            @change="onEditorChange($event)">
        </quill-editor>
    </div>
</template>
<script>
import { quillEditor } from "vue-quill-editor"; //调用编辑器
import { mapGetters, mapActions } from 'vuex'
import 'quill/dist/quill.core.css';
import 'quill/dist/quill.snow.css';
import 'quill/dist/quill.bubble.css';
export default {
    components: {
        quillEditor
    },
    data() {
        return {
            // content: '',
            editorOption: {
                // modules:{
                    // toolbar:[
                        // ['bold', 'italic', 'underline', 'strike'],        // toggled buttons
                        // ['blockquote', 'code-block']
                        // [{'header': 1}, {'header': 2}],               // custom button values
                        // [{'list': 'ordered'}, {'list': 'bullet'}],
                        // [{'indent': '-1'}, {'indent': '+1'}],          // outdent/indent
                        // [{'direction': 'rtl'}],                         // text direction
                        // [{'size': ['small', false, 'large', 'huge']}],  // custom dropdown
                        // [{'header': [1, 2, 3, 4, 5, 6, false]}],
                        // [{'color': []}, {'background': []}],          // dropdown with defaults from theme
                        // [{'font': []}],
                        // [{'align': []}],
                        // ['link'],
                        // ['clean']

                    // ]
                // }
            }
        }
    },
    // props:['content'],
    methods: {
        onEditorReady(editor) { // 准备编辑器
 
        },
        onEditorBlur(){}, // 失去焦点事件
        onEditorFocus(){}, // 获得焦点事件
        onEditorChange(){}, // 内容改变事件
    },
    computed: {
        editor() {
            return this.$refs.myQuillEditor.quill;
        },
        ...mapGetters('subsidizeManager',{
            addObj: "addObj",
            sendInfo: "sendInfo",
        }),
    },
    mounted(){
        // console.log('content',this.content)
    }
}
</script>
