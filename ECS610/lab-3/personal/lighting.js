// ECS610U -- Miles Hansard 2021
'use strict';
var canvas, gl;
const vs_file = './lighting-vert.glsl';
const fs_file = './lighting-frag.glsl';

// illuminant properties
// B2 -- MODIFY
var light = {
    position: [0.0, 0.0, -6.5, 1.0],
    ambient:  [1.0, 1.0, 1.0, 1.0],
    diffuse:  [1.0, 1.0, 1.0, 1.0],
    specular: [1.0, 1.0, 1.0, 1.0],
};

// material properties
// B1 -- MODIFY

// A2 -- CHANGE THIS
var num_models = 100;

// modelview parameters
var transform = [];

// viewing parameters
let vert_fov_deg = 45.0;
let near = 2.5;
let far = 12.0;
let aspect = 1;
var theta = 0.0;

// buffers and attributes
var projection, modelview, animate = false;

// uniform locations
var vertex_loc, normal_loc, projection_loc, modelview_loc;

var meshes_list, program

async function init(meshes)
{
    // choose mesh from incoming list
    meshes_list = [meshes.banana, meshes.sphere, meshes.suzanne]
    // set button to save the image
    capture_canvas_setup('gl-canvas', 'capture-button', 'capture.png');

    canvas = document.getElementById("gl-canvas");

    // boolean flag for mouse-click enabling/disabling of object motion 
    canvas.onclick = function() { animate = !animate; }

    gl = canvas.getContext('webgl', { alpha:false });

    // load the shader source code into JS strings
    const vs_src = await fetch(vs_file).then(out => out.text());
    const fs_src = await fetch(fs_file).then(out => out.text());
    // make the shaders and link them together as a program
    let vs = webgl_make_shader(gl, vs_src, gl.VERTEX_SHADER);
    let fs = webgl_make_shader(gl, fs_src, gl.FRAGMENT_SHADER);
    program = webgl_make_program(gl, vs, fs);
    gl.useProgram(program);

    vertex_loc = gl.getAttribLocation(program, 'vertex');
    gl.enableVertexAttribArray(vertex_loc);

    normal_loc = gl.getAttribLocation(program, 'normal');
    gl.enableVertexAttribArray(normal_loc);

    // gl buffers will be created automatically by shared/webgl-obj-loader.js 
    

    for (let mesh of meshes_list) {
    // setup vertex attributes
    OBJ.initMeshBuffers(gl, mesh);
    }

    // --- set light and material properties in shader

    // note that the GLSL/JS objects have the same property names as in the JS
    // hence the JS property names can be used to find the uniform locations
    for(let property in light) {
        gl.uniform4fv(gl.getUniformLocation(program,'light.'+property), 
                      light[property]);
    }

    gl.uniform1f(gl.getUniformLocation(program,'near'), near);
    gl.uniform1f(gl.getUniformLocation(program,'far'), far);

    // --- get uniform locations ---

    modelview_loc = gl.getUniformLocation(program, 'modelview');
    projection_loc = gl.getUniformLocation(program, 'projection');

    // --- rendering setup ---

    gl.viewport(0, 0, canvas.width, aspect*canvas.height);
    gl.clearColor(0.15, 0.15, 0.15, 1.0);
    gl.lineWidth(1.0);
    gl.enable(gl.DEPTH_TEST);
    gl.depthFunc(gl.LEQUAL);
    gl.enable(gl.BLEND);
    gl.blendFunc(gl.SRC_ALPHA, gl.ONE_MINUS_SRC_ALPHA);

    // projection matrix from shared/graphics.js
    projection = mat_perspective(vert_fov_deg, aspect, near, far);

    // random modelview parameters
    for(let k = 0; k < num_models; k++) {
        // empty struct
        transform[k] = {};
        // set random size, location, and rotation axis for each model
        transform[k].mesh_id = Math.floor(Math.random()*meshes_list.length);
        let scale = random(1, 10)
        if (transform[k].mesh_id > 0) {
            scale /= 25
        }
        transform[k].scale = vec_scale(scale, [1,1,1]);
        transform[k].location = [random(-2,2), random(-2,2), -(far+near)/2 + random(-2,2)];
        transform[k].axis = [random(-1,1), random(-1,1), random(-1,1)];

        transform[k].theta = 0
        transform[k].theta_step = random(0.001, 0.3) * 2
        transform[k].material = {
            ambient:  [random(0, 1), random(0, 1), random(0, 1), random(0, 1)],
            diffuse:  [random(0, 1), random(0, 1), random(0, 1), random(0, 1)],
            specular: [random(0, 1), random(0, 1), random(0, 1), random(0, 1)],
            shininess: random(5, 100)
        };
    }

    render();
}
const z = (far + near)/2;
var eye = [0, 0, 1]
var rot_step = 0
async function render() 
{
    // clear buffers and send projection matrix to shaders
    gl.clear(gl.COLOR_BUFFER_BIT | gl.DEPTH_BUFFER_BIT);
    gl.uniformMatrix4fv(projection_loc, false, mat_float_flat_transpose(projection));

    // transformation and rendering 
    gl.uniform1i(gl.getUniformLocation(program,'ray'), 0);
    for(let k = 0; k < num_models; k++){
        let material = transform[k].material
        for(let property in material) {
            if(property != 'shininess')
                gl.uniform4fv(gl.getUniformLocation(program, 'material.'+property), 
                              material[property]);
        }
        gl.uniform1f(gl.getUniformLocation(program,'material.shininess'), material.shininess);

        // A1, A2 -- MODIFY HERE
        transform[k].theta += transform[k].theta_step * animate;

        // average scene depth
        

        // 4x4 rigid motion
        let motion = mat_motion(transform[k].theta, transform[k].axis, transform[k].location);

        // uniform scaling
        let scaling = mat_scaling(transform[k].scale)



        modelview = mat_prod(motion, scaling);
    


        gl.uniformMatrix4fv(modelview_loc, false, mat_float_flat_transpose(modelview));
        let mesh = meshes_list[transform[k].mesh_id]

        gl.bindBuffer(gl.ARRAY_BUFFER, mesh.vertexBuffer);
        gl.vertexAttribPointer(vertex_loc, mesh.vertexBuffer.itemSize, gl.FLOAT, false, 0, 0);
    
        // setup normal attributes
        gl.bindBuffer(gl.ARRAY_BUFFER, mesh.normalBuffer);
        gl.vertexAttribPointer(normal_loc, mesh.normalBuffer.itemSize, gl.FLOAT, false, 0, 0);
    
        // setup face indices
        gl.bindBuffer(gl.ELEMENT_ARRAY_BUFFER, mesh.indexBuffer);

        gl.drawElements(gl.TRIANGLES, mesh.indexBuffer.numItems, gl.UNSIGNED_SHORT, 0);

        //gl.uniform1i(gl.getUniformLocation(program,'ray'), 1);
        var ray_buffer = gl.createBuffer()
        gl.bindBuffer(gl.ARRAY_BUFFER, ray_buffer);
        let ray_data = new Float32Array([transform[k].location[0], transform[k].location[1], transform[k].location[2],
            light.position[0], light.position[1], light.position[2]])
        gl.bufferData(gl.ARRAY_BUFFER, ray_data, gl.STATIC_DRAW);

        gl.vertexAttribPointer(vertex_loc, 3, gl.FLOAT, false, 0, 0);
        //gl.drawElements(gl.LINES, 2, gl.UNSIGNED_SHORT, 0);
    }
    render_light()

    // check if screen capture requested
    capture_canvas_check();
    
    // ask browser to call render() again, after 1/60 second
    window.setTimeout(render, 1000/60);
}

var light_var = 0
async function render_light() {
    gl.uniform1i(gl.getUniformLocation(program,'ray'), 0);
    let light_obj = {
        material: {
            ambient:  [1, 1, 1, 1],
            diffuse:  [1, 1, 1, 1],
            specular: [1, 1, 1, 1],
            shininess: 5
        },
        scale: vec_scale(0.3, [1,1,1])   
    }
    const light_step = 0.1 * animate
    let x = light.position[0], zz = light.position[2]
    light.position[0] = x * Math.cos(light_step) + zz * Math.sin(light_step);
    light.position[2] = -x * Math.sin(light_step) + zz * Math.cos(light_step);
    

    let translation = [[1, 0,  0, light.position[0]],
    [0, 1, 0, light.position[1]],
    [0, 0, 1, light.position[2]], // -6.5
    [0, 0, 0, 1]];


    for(let property in light) {
        gl.uniform4fv(gl.getUniformLocation(program,'light.'+property), 
                      light[property]);
    }

    let material = light_obj.material
    for(let property in material) {
        if(property != 'shininess')
            gl.uniform4fv(gl.getUniformLocation(program, 'material.'+property), 
                            material[property]);
    }
    gl.uniform1f(gl.getUniformLocation(program,'material.shininess'), material.shininess);

    // A1, A2 -- MODIFY HERE
    //transform[k].theta += transform[k].theta_step * animate;

    // average scene depth
    let z = (far + near)/2;

    // 4x4 rigid motion
    //let motion = mat_motion(transform[k].theta, transform[k].axis, transform[k].location);

    // uniform scaling
    let scaling = mat_scaling(light_obj.scale)

    //rot_step += 0.000005 * animate

    modelview = mat_prod(translation, scaling);
    //modelview = mat_prod(rotation, modelview)
    //mat_console_log(modelview)


    gl.uniformMatrix4fv(modelview_loc, false, mat_float_flat_transpose(modelview));
    let mesh = meshes_list[1]

    gl.bindBuffer(gl.ARRAY_BUFFER, mesh.vertexBuffer);
    gl.vertexAttribPointer(vertex_loc, mesh.vertexBuffer.itemSize, gl.FLOAT, false, 0, 0);

    // setup normal attributes
    gl.bindBuffer(gl.ARRAY_BUFFER, mesh.normalBuffer);
    gl.vertexAttribPointer(normal_loc, mesh.normalBuffer.itemSize, gl.FLOAT, false, 0, 0);

    // setup face indices
    gl.bindBuffer(gl.ELEMENT_ARRAY_BUFFER, mesh.indexBuffer);

    gl.drawElements(gl.LINES, mesh.indexBuffer.numItems, gl.UNSIGNED_SHORT, 0);
}

window.onload = async function()
{
    // load the mesh and trigger init()
    // B1 -- MODIFY
    const MODELS_DIR = '../shared/models/';
    OBJ.downloadMeshes({
        'banana': MODELS_DIR + 'banana.obj',
        'sphere': MODELS_DIR + 'sphere.obj',
        'suzanne': MODELS_DIR + 'suzanne.obj',
    }, init);
}
